package org.example.project.data

import org.example.project.currentInstant

/**
 * Intelligent scan processor with validation, offline support, and error handling.
 * Implements hybrid MLKit/ZXing scanner logic with automatic fallback.
 */
class ScanProcessor(
    private val ticketRepository: TicketRepository
) {
    
    /**
     * Process a scanned QR/barcode with comprehensive validation.
     * Supports offline mode with automatic queueing.
     */
    suspend fun processScan(
        qrData: String,
        scannerId: String,
        scannerName: String,
        scannerEngine: ScannerEngine = ScannerEngine.AUTO,
        offlineMode: Boolean = false
    ): ScanResult {
        // 1. Extract and validate ticket ID from QR data
        val ticketId = extractTicketId(qrData)
        if (ticketId.isBlank()) {
            return ScanResult.Error(
                message = "Invalid QR code format",
                errorCode = ScanErrorCode.INVALID_QR_DATA
            )
        }
        
        // 2. If offline mode, queue immediately
        if (offlineMode) {
            return ticketRepository.scanTicket(
                ticketId = ticketId,
                scannerId = scannerId,
                scannerName = scannerName,
                offline = true
            ).getOrElse { error ->
                ScanResult.Error(
                    message = error.message ?: "Offline scan failed",
                    errorCode = ScanErrorCode.UNKNOWN_ERROR
                )
            }
        }
        
        // 3. Fetch ticket from Firestore
        val ticketResult = ticketRepository.getTicket(ticketId)
        val ticket = ticketResult.getOrNull()
        
        if (ticket == null) {
            return ScanResult.Error(
                message = "Ticket not found in database",
                errorCode = ScanErrorCode.TICKET_NOT_FOUND
            )
        }
        
        // 4. Check if already scanned (idempotent check)
        if (ticket.scanned) {
            return ScanResult.AlreadyScanned(
                ticket = ticket,
                originalScanTime = ticket.scannedAt,
                originalScanner = ticket.scannedBy
            )
        }
        
        // 5. Validate ticket status
        if (!isValidStatus(ticket.status)) {
            return ScanResult.Error(
                message = "Ticket status is '${ticket.status}'. Cannot scan.",
                errorCode = ScanErrorCode.INVALID_STATUS
            )
        }
        
        // 6. Check if ticket is prohibited from scanning
        if (ticket.scanProhibited) {
            return ScanResult.Error(
                message = "This ticket has been prohibited from scanning",
                errorCode = ScanErrorCode.DUPLICATE_SCAN
            )
        }
        
        // 7. Validate ticket hasn't expired (based on event date)
        if (isTicketExpired(ticket.dateLabel)) {
            return ScanResult.Error(
                message = "Ticket has expired",
                errorCode = ScanErrorCode.EXPIRED_TICKET
            )
        }
        
        // 8. Perform the scan operation
        return ticketRepository.scanTicket(
            ticketId = ticketId,
            scannerId = scannerId,
            scannerName = scannerName,
            offline = false
        ).getOrElse { error ->
            // Map exception to appropriate error code
            val errorCode = when {
                error.message?.contains("permission", ignoreCase = true) == true -> 
                    ScanErrorCode.PERMISSION_DENIED
                error.message?.contains("network", ignoreCase = true) == true -> 
                    ScanErrorCode.NETWORK_ERROR
                else -> ScanErrorCode.UNKNOWN_ERROR
            }
            
            ScanResult.Error(
                message = error.message ?: "Scan failed",
                errorCode = errorCode
            )
        }
    }
    
    /**
     * Extract ticket ID from QR code data.
     * Supports multiple formats:
     * - Direct ticket ID: "TKT-123456"
     * - JSON format: {"ticketId": "TKT-123456", ...}
     * - URL format: "https://goticky.com/ticket/TKT-123456"
     */
    fun extractTicketId(qrData: String): String {
        return when {
            // Direct ticket ID format
            qrData.startsWith("TKT-") || qrData.startsWith("tkt-") -> qrData
            
            // URL format
            qrData.contains("/ticket/") -> {
                qrData.substringAfterLast("/ticket/").substringBefore("?")
            }
            
            // JSON format (simple extraction)
            qrData.contains("\"ticketId\"") -> {
                val regex = "\"ticketId\"\\s*:\\s*\"([^\"]+)\"".toRegex()
                regex.find(qrData)?.groupValues?.getOrNull(1) ?: ""
            }
            
            // QR seed format (legacy)
            qrData.length >= 16 -> qrData
            
            else -> ""
        }
    }
    
    /**
     * Validate ticket status is acceptable for scanning.
     */
    private fun isValidStatus(status: String): Boolean {
        return status in listOf(
            "Valid",
            "Pending entry",
            "Active",
            "Confirmed"
        )
    }
    
    /**
     * Check if ticket has expired based on event date.
     * Simple implementation - can be enhanced with actual date parsing.
     */
    private fun isTicketExpired(dateLabel: String): Boolean {
        // TODO: Implement actual date comparison
        // For now, assume tickets don't expire
        return false
    }
    
    /**
     * Batch process multiple scans (for bulk operations).
     */
    suspend fun processBatchScans(
        qrDataList: List<String>,
        scannerId: String,
        scannerName: String
    ): List<ScanResult> {
        return qrDataList.map { qrData ->
            processScan(
                qrData = qrData,
                scannerId = scannerId,
                scannerName = scannerName
            )
        }
    }
    
    /**
     * Validate QR data format before attempting scan.
     */
    fun validateQrFormat(qrData: String): Boolean {
        return extractTicketId(qrData).isNotBlank()
    }
    
    /**
     * Get recommended scanner engine based on connectivity and ticket type.
     */
    fun getRecommendedEngine(
        hasInternet: Boolean,
        ticketType: TicketType? = null
    ): ScannerEngine {
        return when {
            !hasInternet -> ScannerEngine.ZXING // Offline mode
            hasInternet -> ScannerEngine.MLKIT  // Online mode with ML features
            else -> ScannerEngine.AUTO
        }
    }
}
