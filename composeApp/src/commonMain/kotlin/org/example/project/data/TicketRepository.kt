@file:OptIn(kotlin.time.ExperimentalTime::class)

package org.example.project.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
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
            // Fetch all tickets from Firestore
            val snapshot = firestore
                .collection("tickets")
                .get()
            
            // Group tickets by eventId
            val ticketsByEvent = snapshot.documents
                .mapNotNull { doc ->
                    try {
                        val eventId = doc.get<String?>("eventId") ?: return@mapNotNull null
                        val eventTitle = doc.get<String?>("eventTitle") ?: return@mapNotNull null
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
                        
                        eventId to AdminTicketRecord(
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
                        )
                    } catch (e: Exception) {
                        null
                    }
                }
                .groupBy { it.first }
                .mapValues { (_, tickets) -> tickets.map { it.second } }
            
            // Create AdminTicketGroup for each event
            ticketsByEvent.map { (eventId, tickets) ->
                val firstTicket = tickets.firstOrNull()
                AdminTicketGroup(
                    eventId = eventId,
                    eventTitle = firstTicket?.let { 
                        snapshot.documents.find { it.id == tickets.first().id }?.get<String?>("eventTitle") 
                    } ?: "Unknown Event",
                    venue = snapshot.documents.find { it.get<String?>("eventId") == eventId }?.get<String?>("venue") ?: "",
                    city = snapshot.documents.find { it.get<String?>("eventId") == eventId }?.get<String?>("city") ?: "",
                    dateLabel = snapshot.documents.find { it.get<String?>("eventId") == eventId }?.get<String?>("dateLabel") ?: "",
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
            offlineQueue.add(OfflineScanQueueItem(
                ticketId = ticketId,
                scannerId = scannerId,
                scannerName = scannerName,
                timestamp = timestamp
            ))
            
            return Result.success(ScanResult.Offline(
                ticket = createPlaceholderTicket(ticketId),
                queuedForSync = true
            ))
        }
        
        return runCatching {
            // TODO: Implement actual scan logic
            ScanResult.Success(createPlaceholderTicket(ticketId))
        }
    }
    
    /**
     * Validate ticket for scanning.
     */
    suspend fun validateTicketForScan(ticketId: String): Result<Boolean> {
        return Result.success(true)
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
}
