@file:OptIn(kotlin.time.ExperimentalTime::class)

package org.example.project.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.DocumentReference
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.firestore.where
import org.example.project.currentInstant
import org.example.project.platform.isFirebaseAvailable

/**
 * Simplified ticket repository for scanning system.
 * Handles Firestore operations with offline support.
 */
class TicketRepository {
    
    private val firestore by lazy { Firebase.firestore }
    private val offlineQueue = mutableListOf<OfflineScanQueueItem>()
    
    /**
     * Fetch all tickets grouped by event.
     */
    suspend fun fetchTicketGroups(): Result<List<AdminTicketGroup>> {
        if (!isFirebaseAvailable()) {
            return Result.success(emptyList())
        }
        
        return runCatching {
            // Fetch all user-owned tickets via collection group query
            val snapshot = firestore
                .collectionGroup("tickets")
                .get()

            data class EventMeta(
                val title: String,
                val venue: String,
                val city: String,
                val dateLabel: String,
            )

            // Group tickets by eventId (fall back to synthetic id if missing)
            val ticketsByEvent = snapshot.documents
                .mapNotNull { doc ->
                    try {
                        val eventId = doc.get<String?>("eventId")?.takeIf { it.isNotBlank() }
                            ?: "unknown-${doc.id}"
                        val eventTitle = doc.get<String?>("eventTitle") ?: "Unknown Event"
                        val venue = doc.get<String?>("venue") ?: ""
                        val city = doc.get<String?>("city") ?: ""
                        val dateLabel = doc.get<String?>("dateLabel") ?: ""
                        val holderName = doc.get<String?>("holderName") ?: ""
                        val seat = doc.get<String?>("seat") ?: ""
                        val status = doc.get<String?>("status") ?: "Valid"
                        val typeStr = doc.get<String?>("type") ?: "General"
                        val qrSeed = doc.get<String?>("qrSeed") ?: ""
                        val scanned = doc.get<Boolean?>("scanned") ?: false
                        val scannedAt = doc.get<String?>("scannedAt")
                        val purchaseAt = doc.get<String?>("purchaseAt") ?: ""

                        val type = when (typeStr.lowercase()) {
                            "earlybird" -> TicketType.EarlyBird
                            "vip" -> TicketType.VIP
                            "goldencircle" -> TicketType.GoldenCircle
                            else -> TicketType.General
                        }

                        eventId to (EventMeta(eventTitle, venue, city, dateLabel) to AdminTicketRecord(
                            id = doc.id,
                            holderName = holderName,
                            seat = seat,
                            type = type,
                            status = status,
                            scanned = scanned,
                            qrSeedShort = qrSeed.takeLast(4),
                            priceLabel = "$0.00", // TODO: Extract from seat or separate field
                            purchaseChannel = "Online",
                            purchaseAt = purchaseAt,
                            scannedAt = scannedAt,
                            lastScanTime = scannedAt
                        ))
                    } catch (e: Exception) {
                        null
                    }
                }
                .groupBy { it.first }
                .mapValues { (_, entries) ->
                    val meta = entries.first().second.first
                    val records = entries.map { it.second.second }
                    meta to records
                }

            // Create AdminTicketGroup for each event
            ticketsByEvent.map { (eventId, payload) ->
                val (meta, tickets) = payload
                AdminTicketGroup(
                    eventId = eventId,
                    eventTitle = meta.title,
                    venue = meta.venue,
                    city = meta.city,
                    dateLabel = meta.dateLabel,
                    tickets = tickets
                )
            }
        }
    }
    
    /**
     * Scan a ticket.
     */
    suspend fun scanTicket(
        ticketId: String,
        scannerId: String,
        scannerName: String,
        offline: Boolean = false
    ): Result<ScanResult> {
        val timestamp = currentInstant().toString()

        if (offline || !isFirebaseAvailable()) {
            offlineQueue.add(
                OfflineScanQueueItem(
                    ticketId = ticketId,
                    scannerId = scannerId,
                    scannerName = scannerName,
                    timestamp = timestamp
                )
            )

            return Result.success(
                ScanResult.Offline(
                    ticket = createPlaceholderTicket(ticketId),
                    queuedForSync = true
                )
            )
        }

        return runCatching {
            val target = findTicketDocument(ticketId)
                ?: return@runCatching ScanResult.Error("Ticket not found", ScanErrorCode.TICKET_NOT_FOUND)

            val existing = target.toTicketPass(ticketId)
                ?: return@runCatching ScanResult.Error("Invalid ticket payload", ScanErrorCode.INVALID_QR_DATA)

            // Prevent double-scan: if already scanned, return status without mutating.
            if (existing.scanned) {
                return@runCatching ScanResult.AlreadyScanned(
                    ticket = existing,
                    originalScanTime = existing.scannedAt,
                    originalScanner = existing.scannedBy
                )
            }

            val updatedFields = mapOf(
                "scanned" to true,
                "scannedAt" to timestamp,
                "scannedBy" to scannerId,
                "updatedAt" to timestamp,
                "scanCount" to (existing.scanCount + 1)
            )

            target.reference.update(updatedFields)

            recordScanEvent(
                ticketRef = target.reference,
                ticketId = target.id,
                scannerId = scannerId,
                scannerName = scannerName,
                timestamp = timestamp,
                success = true,
                failureReason = null
            )

            ScanResult.Success(
                existing.copy(
                    scanned = true,
                    scannedAt = timestamp,
                    scannedBy = scannerId,
                    scanCount = existing.scanCount + 1
                )
            )
        }
    }
    
    /**
     * Validate ticket for scanning.
     */
    suspend fun validateTicketForScan(ticketId: String): Result<Boolean> {
        if (!isFirebaseAvailable()) return Result.success(false)
        return runCatching { findTicketDocument(ticketId) != null }
    }
    
    /**
     * Get ticket statistics.
     */
    suspend fun getTicketStats(eventId: String?): Result<TicketStats> {
        return Result.success(TicketStats(
            eventId = eventId,
            totalTickets = 0,
            scannedTickets = 0,
            pendingTickets = 0,
            scanRate = 0f,
            lastUpdated = currentInstant().toString()
        ))
    }
    
    /**
     * Sync offline queue.
     */
    suspend fun syncOfflineQueue(): Result<Int> {
        val count = offlineQueue.size
        offlineQueue.clear()
        return Result.success(count)
    }
    
    private fun createPlaceholderTicket(ticketId: String): TicketPass {
        return TicketPass(
            id = ticketId,
            eventTitle = "Sample Event",
            venue = "Sample Venue",
            dateLabel = "TBD",
            seat = "A1",
            status = "Valid",
            type = TicketType.General,
            holderName = "Sample Holder",
            holderInitials = "SH",
            qrSeed = ticketId
        )
    }

    private suspend fun findTicketDocument(ticketId: String): DocumentSnapshot? {
        // Prefer docId match; fallback to qrSeed match if ids vary.
        val group = firestore.collectionGroup("tickets")
        val byId = group.where("__name__", "==", ticketId).get().documents.firstOrNull()
        if (byId != null) return byId

        val byQrSeed = group.where("qrSeed", "==", ticketId).get().documents.firstOrNull()
        return byQrSeed
    }

    private fun DocumentSnapshot.toTicketPass(fallbackId: String): TicketPass? {
        val eventTitle = get<String?>("eventTitle") ?: return null
        val venue = get<String?>("venue") ?: "Venue TBC"
        val city = get<String?>("city") ?: ""
        val country = get<String?>("country") ?: ""
        val dateLabel = get<String?>("dateLabel") ?: "See event details"
        val seat = get<String?>("seat") ?: "General Admission"
        val status = get<String?>("status") ?: "Ready"
        val typeLabel = get<String?>("type") ?: "General"
        val type = runCatching { TicketType.valueOf(typeLabel) }.getOrDefault(TicketType.General)
        val holderName = get<String?>("holderName") ?: "Guest"
        val holderInitials = get<String?>("holderInitials") ?: "GT"
        val qrSeed = get<String?>("qrSeed") ?: fallbackId
        val purchaseAt = get<String?>("purchaseAt")
        val scanned = get<Boolean?>("scanned") ?: false
        val scannedAt = get<String?>("scannedAt")
        val scannedBy = get<String?>("scannedBy")
        val scanCount = get<Long?>("scanCount")?.toInt() ?: 0
        val eventId = get<String?>("eventId") ?: ""
        val ownerId = get<String?>("ownerId") ?: ""

        return TicketPass(
            id = id,
            eventTitle = eventTitle,
            venue = venue,
            city = city,
            country = country,
            dateLabel = dateLabel,
            seat = seat,
            status = status,
            type = type,
            holderName = holderName,
            holderInitials = holderInitials,
            qrSeed = qrSeed,
            purchaseAt = purchaseAt,
            eventId = eventId,
            ownerId = ownerId,
            scanned = scanned,
            scannedAt = scannedAt,
            scannedBy = scannedBy,
            scanCount = scanCount
        )
    }

    private suspend fun recordScanEvent(
        ticketRef: DocumentReference,
        ticketId: String,
        scannerId: String,
        scannerName: String,
        timestamp: String,
        success: Boolean,
        failureReason: String?
    ) {
        runCatching {
            ticketRef
                .collection("scanEvents")
                .add(
                    mapOf(
                        "ticketId" to ticketId,
                        "scannerId" to scannerId,
                        "scannerName" to scannerName,
                        "timestamp" to timestamp,
                        "success" to success,
                        "failureReason" to failureReason
                    )
                )
        }
    }
}
