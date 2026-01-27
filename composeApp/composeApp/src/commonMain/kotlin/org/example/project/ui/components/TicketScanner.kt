package org.example.project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.project.data.ScanResult
import org.example.project.data.ScannerConfig

/**
 * Main ticket scanner screen.
 */
@Composable
fun TicketScanner(
    scannerConfig: ScannerConfig,
    onScanResult: (ScanResult) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    var flashlightEnabled by remember { mutableStateOf(false) }
    var lastResult by remember { mutableStateOf<ScanResult?>(null) }
    
    Box(modifier = modifier.fillMaxSize()) {
        // Platform-specific scanner implementation
        PlatformTicketScanner(
            scannerConfig = scannerConfig.copy(enableFlashlight = flashlightEnabled),
            onScanResult = { result ->
                lastResult = result
                onScanResult(result)
            },
            onClose = onClose,
            modifier = Modifier.fillMaxSize()
        )
        
        // Overlay UI
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .safeDrawingPadding()
        ) {
            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onClose,
                    colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Black.copy(alpha = 0.5f))
                ) {
                    Icon(Icons.Default.ArrowBack, "Back", tint = Color.White)
                }
                
                IconButton(
                    onClick = { flashlightEnabled = !flashlightEnabled },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = if (flashlightEnabled) Color.Yellow else Color.Black.copy(alpha = 0.5f)
                    )
                ) {
                    Icon(
                        if (flashlightEnabled) Icons.Default.FlashOn else Icons.Default.FlashOff,
                        "Flashlight",
                        tint = if (flashlightEnabled) Color.Black else Color.White
                    )
                }
            }
            
            Spacer(Modifier.weight(1f))
            
            // Scanner guide
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Align QR code within frame",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.5f), MaterialTheme.shapes.small)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
        
        // Result Overlay
        ScanResultOverlay(
            result = lastResult,
            onDismiss = { lastResult = null }
        )
    }
}
