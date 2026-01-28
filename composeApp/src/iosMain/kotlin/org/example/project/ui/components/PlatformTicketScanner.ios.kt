package org.example.project.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.data.ScanResult
import org.example.project.data.ScannerConfig

/**
 * iOS implementation of PlatformTicketScanner.
 * TODO: Implement native iOS camera scanning
 */
@Composable
actual fun PlatformTicketScanner(
    scannerConfig: ScannerConfig,
    onScanResult: (ScanResult) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Camera scanning not yet implemented for iOS.\nPlease use Android app for scanning.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}
