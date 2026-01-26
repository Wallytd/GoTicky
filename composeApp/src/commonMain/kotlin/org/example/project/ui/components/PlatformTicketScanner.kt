package org.example.project.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.example.project.data.ScannerConfig
import org.example.project.data.ScanResult

@Composable
expect fun PlatformTicketScanner(
    scannerConfig: ScannerConfig,
    onScanResult: (ScanResult) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
)
