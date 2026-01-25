package org.example.project.ui.components

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.example.project.data.ScannerConfig
import org.example.project.data.ScannerEngine
import org.example.project.data.ScanResult

/**
 * Android-specific ticket scanner with full camera integration.
 * This is the production-ready version with MLKit and ZXing support.
 */
@Composable
expect fun PlatformTicketScanner(
    scannerConfig: ScannerConfig,
    onScanResult: (ScanResult) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
)
