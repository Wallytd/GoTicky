package org.example.project.data

/**
 * Admin ticket group - tickets grouped by event
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
 * Admin ticket record - individual ticket with scan tracking
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
    val lastScanTime: String? = null
)

/**
 * Ticket scan event - immutable audit trail
 */
data class TicketScanEvent(
    val ticketId: String,
    val scannerId: String,
    val scannerName: String,
    val timestamp: String,
    val success: Boolean,
    val failureReason: String? = null,
    val location: String? = null,
    val deviceInfo: String? = null
)

/**
 * Ticket statistics for analytics
 */
data class TicketStats(
    val eventId: String?,
    val totalTickets: Int,
    val scannedTickets: Int,
    val pendingTickets: Int,
    val scanRate: Float,
    val lastUpdated: String
)

/**
 * Scan result sealed class
 */
sealed class ScanResult {
    data class Success(val ticket: TicketPass) : ScanResult()
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
 * Scan error codes
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
 * Scanner engine types
 */
enum class ScannerEngine {
    MLKIT,  // Google MLKit (online)
    ZXING,  // ZXing (offline)
    AUTO,   // Automatic selection
    MANUAL  // Manual entry
}

/**
 * Scanner configuration
 */
data class ScannerConfig(
    val preferredEngine: ScannerEngine = ScannerEngine.AUTO,
    val enableFlashlight: Boolean = false,
    val enableHapticFeedback: Boolean = true,
    val offlineMode: Boolean = false,
    val autoSyncInterval: Long = 30000 // 30 seconds
)

/**
 * Offline scan queue item
 */
data class OfflineScanQueueItem(
    val ticketId: String,
    val scannerId: String,
    val scannerName: String,
    val timestamp: String,
    val retryCount: Int = 0
)
