@file:OptIn(kotlin.time.ExperimentalTime::class)

package org.example.project.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch
import org.example.project.currentInstant
import org.example.project.platform.isFirebaseAvailable
import kotlin.random.Random

/**
 * Repository for managing tickets with offline-first architecture.
 * Supports hybrid MLKit/ZXing scanning with automatic fallback.
 */
class TicketRepository {
    
    private val firestore by lazy { Firebase.firestore }
    private val auth by lazy { Firebase.auth }
    
    // Offline queue for pending scans
    private val offlineQueue = mutableListOf<OfflineScanQueueItem>()
    
    /**
     * Fetch all tickets for a specific event with real-time updates.
     */
    fun fetchTicketsForEvent(eventId: String): Flow<Result<List<TicketPass>>> = flow {
        if (!isFirebaseAvailable()) {
            emit(Result.failure(IllegalStateException("Firebase unavailable")))
            return@flow
        }
        
        try {
            val snapshot = firestore
                .collection("tickets")
                .where { "eventId" equalTo eventId }
                .orderBy("purchaseAt")
                .get()
            
            val tickets = snapshot.documents.mapNotNull { doc ->
                parseTicketFromDocument(doc)
            }
            
            emit(Result.success(tickets))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.catch { e ->
        emit(Result.failure(e))
    }
    
    /**
     * Fetch all tickets across all events (admin view).
     */
    suspend fun fetchAllTickets(): Result<List<TicketPass>> {
        if (!isFirebaseAvailable()) {
            return Result.failure(IllegalStateException("Firebase unavailable"))
        }
        
        return runCatching {
            val snapshot = firestore
                .collection("tickets")
                .orderBy("eventId")
                .orderBy("purchaseAt")
                .get()
            
            snapshot.documents.mapNotNull { doc ->
                parseTicketFromDocument(doc)
            }
        }
    }
    
    /**
     * Fetch tickets grouped by event for admin dashboard.
     */
    suspend fun fetchTicketGroups(): Result<List<AdminTicketGroup>> {
        if (!isFirebaseAvailable()) {
            return Result.failure(IllegalStateException("Firebase unavailable"))
        }
        
        return runCatching {
            val tickets = fetchAllTickets().getOrThrow()
            
            // Group tickets by event
            val grouped = tickets.groupBy { it.eventId }
            
            grouped.map { (eventId, eventTickets) ->
                val firstTicket = eventTickets.first()
                AdminTicketGroup(
                    eventId = eventId,
                    eventTitle = firstTicket.eventTitle,
                    venue = firstTicket.venue,
                    city = firstTicket.city,
                    dateLabel = firstTicket.dateLabel,
                    tickets = eventTickets.map { ticket ->
                        AdminTicketRecord(
                            id = ticket.id,
                            holderName = ticket.holderName,
                            seat = ticket.seat,
                            type = ticket.type,
                            status = ticket.status,
                            scanned = ticket.scanned,
                            qrSeedShort = ticket.qrSeed.takeLast(8),
                            priceLabel = extractPriceFromSeat(ticket.seat),
                            purchaseChannel = "Online", // TODO: Extract from order
                            purchaseAt = ticket.purchaseAt ?: "Unknown",
                            scannedAt = ticket.scannedAt,
                            lastScanTime = ticket.scannedAt,
                            scanCount = ticket.scanCount
                        )
                    }
                )
            }
        }
    }
    
    /**
     * Get a single ticket by ID.
     */
    suspend fun getTicket(ticketId: String): Result<TicketPass?> {
        if (!isFirebaseAvailable()) {
            return Result.failure(IllegalStateException("Firebase unavailable"))
        }
        
        return runCatching {
            val doc = firestore.collection("tickets").document(ticketId).get()
            if (doc.exists) {
                parseTicketFromDocument(doc)
            } else {
                null
            }
        }
    }
    
    /**
     * Validate if a ticket can be scanned.
     */
    suspend fun validateTicketForScan(ticketId: String): Result<Boolean> {
        return runCatching {
            val ticket = getTicket(ticketId).getOrNull()
            when {
                ticket == null -> false
                ticket.scanned -> false
                ticket.scanProhibited -> false
                ticket.status !in listOf("Valid", "Pending entry") -> false
                else -> true
            }
        }
    }
    
    /**
     * Mark a ticket as scanned with offline support.
     * If offline, queues the scan for later sync.
     */
    suspend fun scanTicket(
        ticketId: String,
        scannerId: String,
        scannerName: String,
        offline: Boolean = false
    ): Result<ScanResult> {
        val timestamp = currentInstant().toString()
        
        // If offline, queue the scan
        if (offline || !isFirebaseAvailable()) {
            return queueOfflineScan(ticketId, scannerId, scannerName, timestamp)
        }
        
        return runCatching {
            // Get current ticket state
            val ticket = getTicket(ticketId).getOrNull()
                ?: return@runCatching ScanResult.Error(
                    "Ticket not found",
                    ScanErrorCode.TICKET_NOT_FOUND
                )
            
            // Check if already scanned (idempotent)
            if (ticket.scanned) {
                return@runCatching ScanResult.AlreadyScanned(
                    ticket = ticket,
                    originalScanTime = ticket.scannedAt,
                    originalScanner = ticket.scannedBy
                )
            }
            
            // Validate ticket status
            if (ticket.status !in listOf("Valid", "Pending entry")) {
                return@runCatching ScanResult.Error(
                    "Invalid ticket status: ${ticket.status}",
                    ScanErrorCode.INVALID_STATUS
                )
            }
            
            // Update ticket document directly
            // Note: For production, consider using Firestore transactions for atomic updates
            val docRef = firestore.collection("tickets").document(ticketId)
            
            // Update ticket fields
            docRef.set(mapOf(
                "scanned" to true,
                "scannedAt" to timestamp,
                "scannedBy" to scannerId,
                "scanCount" to (ticket.scanCount + 1),
                "updatedAt" to timestamp
            ), merge = true)
            
            // Record scan event
            val scanEvent = TicketScanEvent(
                id = generateScanEventId(),
                ticketId = ticketId,
                scannerId = scannerId,
                scannerName = scannerName,
                timestamp = timestamp,
                success = true,
                synced = true
            )
            
            recordScanEvent(scanEvent)
            
            // Update statistics
            updateStats(ticket.eventId)
            
            ScanResult.Success(
                ticket = ticket.copy(
                    scanned = true,
                    scannedAt = timestamp,
                    scannedBy = scannerId,
                    scanCount = ticket.scanCount + 1
                ),
                scanEvent = scanEvent
            )
        }
    }
    
    /**
     * Queue a scan for offline processing.
     */
    private suspend fun queueOfflineScan(
        ticketId: String,
        scannerId: String,
        scannerName: String,
        timestamp: String
    ): Result<ScanResult> {
        return runCatching {
            val queueItem = OfflineScanQueueItem(
                id = generateScanEventId(),
                ticketId = ticketId,
                scannerId = scannerId,
                scannerName = scannerName,
                timestamp = timestamp,
                qrData = ticketId
            )
            
            offlineQueue.add(queueItem)
            
            // Try to get cached ticket data
            val ticket = getTicket(ticketId).getOrNull()
                ?: TicketPass(
                    id = ticketId,
                    eventTitle = "Unknown Event",
                    venue = "Unknown Venue",
                    dateLabel = "Unknown Date",
                    seat = "Unknown",
                    status = "Pending sync",
                    type = TicketType.General,
                    holderName = "Unknown",
                    holderInitials = "?",
                    qrSeed = ticketId
                )
            
            ScanResult.Offline(
                ticket = ticket,
                queuedForSync = true
            )
        }
    }
    
    /**
     * Sync offline queue when connection is restored.
     */
    suspend fun syncOfflineQueue(): Result<Int> {
        if (!isFirebaseAvailable() || offlineQueue.isEmpty()) {
            return Result.success(0)
        }
        
        return runCatching {
            var syncedCount = 0
            val failedItems = mutableListOf<OfflineScanQueueItem>()
            
            offlineQueue.forEach { item ->
                val result = scanTicket(
                    ticketId = item.ticketId,
                    scannerId = item.scannerId,
                    scannerName = item.scannerName,
                    offline = false
                )
                
                when (result.getOrNull()) {
                    is ScanResult.Success -> syncedCount++
                    is ScanResult.AlreadyScanned -> syncedCount++ // Already synced
                    else -> {
                        // Retry failed items
                        if (item.retryCount < 3) {
                            failedItems.add(item.copy(
                                retryCount = item.retryCount + 1,
                                lastRetryAt = currentInstant().toString()
                            ))
                        }
                    }
                }
            }
            
            // Clear queue and re-add failed items
            offlineQueue.clear()
            offlineQueue.addAll(failedItems)
            
            syncedCount
        }
    }
    
    /**
     * Record a scan event in the audit trail.
     */
    private suspend fun recordScanEvent(event: TicketScanEvent) {
        if (!isFirebaseAvailable()) return
        
        runCatching {
            firestore
                .collection("tickets")
                .document(event.ticketId)
                .collection("scanEvents")
                .document(event.id)
                .set(mapOf(
                    "ticketId" to event.ticketId,
                    "scannerId" to event.scannerId,
                    "scannerName" to event.scannerName,
                    "timestamp" to event.timestamp,
                    "success" to event.success,
                    "failureReason" to event.failureReason,
                    "location" to event.location,
                    "deviceInfo" to event.deviceInfo
                ))
        }
    }
    
    /**
     * Update ticket statistics for an event.
     */
    private suspend fun updateStats(eventId: String) {
        if (!isFirebaseAvailable()) return
        
        runCatching {
            val tickets = fetchTicketsForEvent(eventId)
            // Stats update logic will be implemented with Flow collection
        }
    }
    
    /**
     * Get ticket statistics for an event or globally.
     */
    suspend fun getTicketStats(eventId: String?): Result<TicketStats> {
        if (!isFirebaseAvailable()) {
            return Result.failure(IllegalStateException("Firebase unavailable"))
        }
        
        return runCatching {
            val tickets = if (eventId != null) {
                fetchTicketsForEvent(eventId)
                // TODO: Collect from Flow
                emptyList()
            } else {
                fetchAllTickets().getOrThrow()
            }
            
            val totalTickets = tickets.size
            val scannedTickets = tickets.count { it.scanned }
            val pendingTickets = totalTickets - scannedTickets
            val scanRate = if (totalTickets == 0) 0f else scannedTickets / totalTickets.toFloat()
            
            TicketStats(
                eventId = eventId,
                totalTickets = totalTickets,
                scannedTickets = scannedTickets,
                pendingTickets = pendingTickets,
                scanRate = scanRate,
                lastUpdated = currentInstant().toString()
            )
        }
    }
    
    // Helper functions
    
    private fun parseTicketFromDocument(doc: DocumentSnapshot): TicketPass? {
        return try {
            TicketPass(
                id = doc.id,
                eventTitle = doc.get<String?>("eventTitle") ?: return null,
                venue = doc.get<String?>("venue") ?: "",
                city = doc.get<String?>("city") ?: "",
                country = doc.get<String?>("country") ?: "",
                dateLabel = doc.get<String?>("dateLabel") ?: "",
                seat = doc.get<String?>("seat") ?: "",
                status = doc.get<String?>("status") ?: "Unknown",
                type = parseTicketType(doc.get<String?>("type")),
                holderName = doc.get<String?>("holderName") ?: "",
                holderInitials = doc.get<String?>("holderInitials") ?: "",
                qrSeed = doc.get<String?>("qrSeed") ?: "",
                purchaseAt = doc.get<String?>("purchaseAt"),
                eventId = doc.get<String?>("eventId") ?: "",
                ownerId = doc.get<String?>("ownerId") ?: "",
                orderId = doc.get<String?>("orderId"),
                scanned = doc.get<Boolean?>("scanned") ?: false,
                scannedAt = doc.get<String?>("scannedAt"),
                scannedBy = doc.get<String?>("scannedBy"),
                scanCount = (doc.get<Long?>("scanCount") ?: 0L).toInt(),
                scanProhibited = doc.get<Boolean?>("scanProhibited") ?: false,
                createdAt = doc.get<String?>("createdAt"),
                updatedAt = doc.get<String?>("updatedAt")
            )
        } catch (e: Exception) {
            null
        }
    }
    
    private fun parseTicketType(typeStr: String?): TicketType {
        return when (typeStr?.lowercase()) {
            "earlybird" -> TicketType.EarlyBird
            "general" -> TicketType.General
            "vip" -> TicketType.VIP
            "goldencircle" -> TicketType.GoldenCircle
            else -> TicketType.General
        }
    }
    
    private fun extractPriceFromSeat(seat: String): String {
        val parts = seat.split("•")
        return parts.getOrNull(1)?.trim() ?: "$0.00"
    }
    
    private fun generateScanEventId(): String {
        val timestamp = currentInstant().toEpochMilliseconds()
        val random = Random.nextInt(1000, 9999)
        return "scan-$timestamp-$random"
    }
}
