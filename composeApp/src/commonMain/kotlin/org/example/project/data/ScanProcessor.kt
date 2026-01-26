package org.example.project.data

import org.example.project.currentInstant

/**
 * Simplified scan processor for QR validation.
 */
class ScanProcessor(private val repository: TicketRepository) {
    
    /**
     * Process a scanned QR code.
     */
    suspend fun processScan(
        qrData: String,
        scannerId: String,
        scannerName: String,
        scannerEngine: ScannerEngine = ScannerEngine.AUTO,
        offlineMode: Boolean = false
    ): ScanResult {
        val ticketId = extractTicketId(qrData)
        
        if (ticketId.isEmpty()) {
            return ScanResult.Error(
                "Invalid QR code",
                ScanErrorCode.INVALID_QR_DATA
            )
        }
        
        val result = repository.scanTicket(ticketId, scannerId, scannerName, offlineMode)
        return result.getOrElse {
            ScanResult.Error(
                it.message ?: "Unknown error",
                ScanErrorCode.UNKNOWN_ERROR
            )
        }
    }
    
    /**
     * Extract ticket ID from QR data.
     */
    fun extractTicketId(qrData: String): String {
        // Direct ID format
        if (qrData.startsWith("TKT-")) {
            return qrData
        }
        
        // URL format
        if (qrData.contains("/ticket/")) {
            return qrData.substringAfterLast("/ticket/").substringBefore("?")
        }
        
        // JSON format
        if (qrData.startsWith("{") && qrData.contains("ticketId")) {
            val match = Regex("\"ticketId\"\\s*:\\s*\"([^\"]+)\"").find(qrData)
            return match?.groupValues?.get(1) ?: ""
        }
        
        return qrData
    }
    
    /**
     * Validate QR format.
     */
    fun validateQrFormat(qrData: String): Boolean {
        return qrData.isNotEmpty()
    }
    
    /**
     * Get recommended scanner engine based on connectivity.
     */
    fun getRecommendedEngine(hasInternet: Boolean): ScannerEngine {
        return if (hasInternet) ScannerEngine.MLKIT else ScannerEngine.ZXING
    }
    
    /**
     * Process batch scans.
     */
    suspend fun processBatchScans(
        qrDataList: List<String>,
        scannerId: String,
        scannerName: String
    ): List<ScanResult> {
        return qrDataList.map { qrData ->
            processScan(qrData, scannerId, scannerName)
        }
    }
}
