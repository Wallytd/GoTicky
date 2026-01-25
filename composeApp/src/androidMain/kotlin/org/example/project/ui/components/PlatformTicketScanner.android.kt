package org.example.project.ui.components

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.data.*
import org.example.project.platform.AndroidCameraPreview
import org.example.project.platform.AndroidCameraPreviewZXing
import org.example.project.platform.rememberHapticFeedback
import org.example.project.platform.isFirebaseAvailable

/**
 * Android implementation of PlatformTicketScanner with full camera support.
 */
@Composable
actual fun PlatformTicketScanner(
    scannerConfig: ScannerConfig,
    onScanResult: (ScanResult) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier
) {
    val context = LocalContext.current
    val haptic = rememberHapticFeedback()
    val scope = rememberCoroutineScope()
    val ticketRepository = remember { TicketRepository() }
    val scanProcessor = remember { ScanProcessor(ticketRepository) }
    
    var isScanning by remember { mutableStateOf(false) }
    var scanResult by remember { mutableStateOf<ScanResult?>(null) }
    var showResult by remember { mutableStateOf(false) }
    
    // Determine which scanner to use
    val useMLKit = remember(scannerConfig.offlineMode) {
        !scannerConfig.offlineMode && isFirebaseAvailable()
    }
    
    // Handle QR detection
    val handleQrDetected: (String) -> Unit = { qrData ->
        if (!isScanning) {
            isScanning = true
            scope.launch {
                val result = scanProcessor.processScan(
                    qrData = qrData,
                    scannerId = "admin-scanner-1", // TODO: Get from auth
                    scannerName = "Admin Scanner",
                    scannerEngine = if (useMLKit) ScannerEngine.MLKIT else ScannerEngine.ZXING,
                    offlineMode = scannerConfig.offlineMode
                )
                
                // Haptic feedback
                when (result) {
                    is ScanResult.Success -> {
                        if (scannerConfig.enableHapticFeedback) {
                            haptic.success()
                        }
                    }
                    is ScanResult.AlreadyScanned -> {
                        if (scannerConfig.enableHapticFeedback) {
                            haptic.warning()
                        }
                    }
                    is ScanResult.Error -> {
                        if (scannerConfig.enableHapticFeedback) {
                            haptic.error()
                        }
                    }
                    is ScanResult.Offline -> {
                        if (scannerConfig.enableHapticFeedback) {
                            haptic.success()
                        }
                    }
                }
                
                scanResult = result
                showResult = true
                onScanResult(result)
                
                // Auto-dismiss and reset
                delay(3000)
                showResult = false
                delay(300)
                isScanning = false
            }
        }
    }
    
    // Use the generic TicketScanner with Android camera preview
    TicketScanner(
        scannerConfig = scannerConfig,
        onScanResult = onScanResult,
        onClose = onClose,
        modifier = modifier
    )
}
