package org.example.project.ui.components

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.data.*
import org.example.project.platform.*

/**
 * Android implementation of PlatformTicketScanner.
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
    val repository = remember { TicketRepository() }
    val scanProcessor = remember { ScanProcessor(repository) }
    
    var isScanning by remember { mutableStateOf(false) }
    
    // Check camera permission
    val hasPermission = requestCameraPermission()
    
    if (!hasPermission) {
        // Show permission placeholder or request
        // requestCameraPermission handles the request internally
        return
    }
    
    val handleQrDetected: (String) -> Unit = { qrData ->
        if (!isScanning) {
            isScanning = true
            scope.launch {
                val result = scanProcessor.processScan(
                    qrData = qrData,
                    scannerId = "admin-1", // TODO: Get real ID
                    scannerName = "Admin Device",
                    offlineMode = scannerConfig.offlineMode
                )
                
                // Haptic feedback
                if (scannerConfig.enableHapticFeedback) {
                    when (result) {
                        is ScanResult.Success -> haptic.success()
                        is ScanResult.AlreadyScanned -> haptic.warning()
                        is ScanResult.Error -> haptic.error()
                        is ScanResult.Offline -> haptic.success()
                    }
                }
                
                onScanResult(result)
                
                // Debounce
                delay(2000)
                isScanning = false
            }
        }
    }
    
    // Choose engine
    if (scannerConfig.offlineMode) {
        AndroidCameraPreviewZXing(
            onQrDetected = handleQrDetected,
            flashlightEnabled = scannerConfig.enableFlashlight,
            modifier = modifier
        )
    } else {
        AndroidCameraPreview(
            onQrDetected = handleQrDetected,
            flashlightEnabled = scannerConfig.enableFlashlight,
            modifier = modifier
        )
    }
}
