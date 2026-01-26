package org.example.project.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.data.ScanResult
import org.example.project.data.TicketType

/**
 * Animated overlay for scan results.
 */
@Composable
fun ScanResultOverlay(
    result: ScanResult?,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = result != null,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut(),
        modifier = modifier
    ) {
        if (result == null) return@AnimatedVisibility
        
        val (backgroundColor, icon, title, message) = when (result) {
            is ScanResult.Success -> ResultConfig(
                Color(0xFF4CAF50).copy(alpha = 0.9f),
                Icons.Default.CheckCircle,
                "Valid Ticket",
                "${result.ticket.holderName}\n${result.ticket.seat} • ${result.ticket.type.name}"
            )
            is ScanResult.AlreadyScanned -> ResultConfig(
                Color(0xFFFF9800).copy(alpha = 0.9f),
                Icons.Default.Warning,
                "Already Scanned",
                "Scanned at ${result.originalScanTime?.substringAfter("T")?.take(5) ?: "Unknown"}\nby ${result.originalScanner ?: "Unknown"}"
            )
            is ScanResult.Error -> ResultConfig(
                Color(0xFFF44336).copy(alpha = 0.9f),
                Icons.Default.Error,
                "Scan Error",
                result.message
            )
            is ScanResult.Offline -> ResultConfig(
                Color(0xFF607D8B).copy(alpha = 0.9f),
                Icons.Default.WifiOff,
                "Offline Scan",
                "Saved to queue. Will sync when online.\n${result.ticket.holderName}"
            )
        }
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable { onDismiss() },
            contentAlignment = Alignment.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = backgroundColor),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = Color.White
                    )
                    
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            }
        }
    }
}

private data class ResultConfig(
    val color: Color,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val title: String,
    val message: String
)
