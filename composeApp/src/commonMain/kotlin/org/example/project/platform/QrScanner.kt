package org.example.project.platform

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.runtime.Composable

/**
 * Scanner engine interface for hybrid MLKit/ZXing implementation.
 * Platform-specific implementations will provide both engines.
 */
interface QrScannerEngine {
    /**
     * Initialize the scanner engine.
     * @param useMLKit If true, use MLKit; if false, use ZXing
     */
    suspend fun initialize(useMLKit: Boolean): Result<Unit>
    
    /**
     * Start scanning for QR codes/barcodes.
     * @return Flow of scanned QR data strings
     */
    suspend fun startScanning(): Result<String>
    
    /**
     * Stop scanning and release resources.
     */
    suspend fun stopScanning()
    
    /**
     * Check if MLKit is available (requires internet for first use).
     */
    suspend fun isMLKitAvailable(): Boolean
    
    /**
     * Check if ZXing is available (always true for offline support).
     */
    fun isZXingAvailable(): Boolean = true
    
    /**
     * Toggle flashlight on/off.
     */
    suspend fun toggleFlashlight(enabled: Boolean)
}

/**
 * Expect function for platform-specific scanner engine.
 */
expect fun createQrScannerEngine(): QrScannerEngine

/**
 * Camera permission handler for scanner.
 */
@Composable
expect fun requestCameraPermission(): Boolean

/**
 * Check if camera permission is granted.
 */
expect fun hasCameraPermission(): Boolean
