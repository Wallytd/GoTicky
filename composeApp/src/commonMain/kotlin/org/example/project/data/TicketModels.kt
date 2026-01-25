package org.example.project.data

import kotlinx.datetime.Instant

/**
 * Represents a group of tickets for a specific event in the admin dashboard.
 * Used for organizing and displaying tickets by event.
 */
data class AdminTicketGroup(
    val eventId: String,
    val eventTitle: String,
    val venue: String,
    val city: String,
    val dateLabel: String,
    val tickets: List<AdminTicketRecord>
)

/**
 * Represents a single ticket record in the admin view with scan tracking.
 * Contains all information needed for admin ticket management and scanning.
 */
data class AdminTicketRecord(
    val id: String,
    val holderName: String,
    val seat: String,
    val type: TicketType,
    val status: String,
    val scanned: Boolean,
    val qrSeedShort: String,
    val priceLabel: String,
    val purchaseChannel: String,
    val purchaseAt: String,
    val scannedAt: String? = null,
    val lastScanTime: String? = null,
    val scanCount: Int = 0
)

/**
 * Represents a single scan event in the audit trail.
 * Stored in Firestore at /tickets/{ticketId}/scanEvents/{scanEventId}
 */
data class TicketScanEvent(
    val id: String = "",
    val ticketId: String,
    val scannerId: String,
    val scannerName: String,
    val timestamp: String,
    val success: Boolean,
    val failureReason: String? = null,
    val location: String? = null,
    val deviceInfo: String? = null,
    val synced: Boolean = false // For offline queue tracking
)

/**
 * Aggregated statistics for ticket scanning.
 * Can be event-specific or global (eventId = null).
 */
data class TicketStats(
    val eventId: String?,
    val totalTickets: Int,
    val scannedTickets: Int,
    val pendingTickets: Int,
    val scanRate: Float,
    val lastUpdated: String,
    val scansByHour: Map<String, Int> = emptyMap(), // For analytics
    val scansByScanner: Map<String, Int> = emptyMap() // For analytics
)

/**
 * Result of a ticket scan operation.
 * Used to communicate scan outcomes to the UI.
 */
sealed class ScanResult {
    data class Success(
        val ticket: TicketPass,
        val scanEvent: TicketScanEvent
    ) : ScanResult()
    
    data class AlreadyScanned(
        val ticket: TicketPass,
        val originalScanTime: String?,
        val originalScanner: String?
    ) : ScanResult()
    
    data class Error(
        val message: String,
        val errorCode: ScanErrorCode
    ) : ScanResult()
    
    data class Offline(
        val ticket: TicketPass,
        val queuedForSync: Boolean
    ) : ScanResult()
}

/**
 * Error codes for scan failures.
 */
enum class ScanErrorCode {
    TICKET_NOT_FOUND,
    INVALID_STATUS,
    EXPIRED_TICKET,
    NETWORK_ERROR,
    PERMISSION_DENIED,
    INVALID_QR_DATA,
    DUPLICATE_SCAN,
    UNKNOWN_ERROR
}

/**
 * Offline scan queue item for syncing when connection is restored.
 */
data class OfflineScanQueueItem(
    val id: String,
    val ticketId: String,
    val scannerId: String,
    val scannerName: String,
    val timestamp: String,
    val qrData: String,
    val retryCount: Int = 0,
    val lastRetryAt: String? = null
)

/**
 * Scanner configuration for hybrid MLKit/ZXing implementation.
 */
data class ScannerConfig(
    val preferredEngine: ScannerEngine = ScannerEngine.AUTO,
    val enableFlashlight: Boolean = false,
    val enableHapticFeedback: Boolean = true,
    val enableAudioFeedback: Boolean = true,
    val autoFocusEnabled: Boolean = true,
    val scanTimeout: Long = 30000, // 30 seconds
    val offlineMode: Boolean = false
)

/**
 * Scanner engine selection for hybrid implementation.
 */
enum class ScannerEngine {
    AUTO,      // Automatically select based on connectivity
    MLKIT,     // Google MLKit (requires internet for first use)
    ZXING,     // ZXing (fully offline)
    MANUAL     // Manual ticket ID entry
}

/**
 * Real-time scan session for tracking active scanning.
 */
data class ScanSession(
    val id: String,
    val scannerId: String,
    val scannerName: String,
    val eventId: String?,
    val startTime: String,
    val endTime: String? = null,
    val totalScans: Int = 0,
    val successfulScans: Int = 0,
    val failedScans: Int = 0,
    val duplicateScans: Int = 0
)
