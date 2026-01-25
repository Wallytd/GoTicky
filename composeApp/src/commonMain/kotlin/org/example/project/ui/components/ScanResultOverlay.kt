package org.example.project.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.example.project.data.ScanResult
import org.example.project.ui.theme.GoTickyGradients
import org.example.project.ui.theme.GoTickyTextures
import org.example.project.ui.theme.goTickyShapes

/**
 * Animated overlay displaying scan results with success/error/already-scanned states.
 */
@Composable
fun ScanResultOverlay(
    result: ScanResult,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        visible = true
        // Auto-dismiss after 3 seconds
        delay(3000)
        visible = false
        delay(300) // Wait for exit animation
        onDismiss()
    }
    
    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(
            initialScale = 0.8f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        ) + fadeIn(),
        exit = scaleOut(targetScale = 0.9f) + fadeOut(),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .clip(goTickyShapes.extraLarge)
                .background(
                    Brush.verticalGradient(
                        colors = when (result) {
                            is ScanResult.Success -> listOf(
                                Color(0xFF1B5E20),
                                Color(0xFF2E7D32)
                            )
                            is ScanResult.AlreadyScanned -> listOf(
                                Color(0xFFE65100),
                                Color(0xFFEF6C00)
                            )
                            is ScanResult.Error -> listOf(
                                Color(0xFFB71C1C),
                                Color(0xFFC62828)
                            )
                            is ScanResult.Offline -> listOf(
                                Color(0xFF424242),
                                Color(0xFF616161)
                            )
                        }
                    )
                )
                .border(
                    width = 2.dp,
                    color = when (result) {
                        is ScanResult.Success -> Color(0xFF4CAF50)
                        is ScanResult.AlreadyScanned -> Color(0xFFFF9800)
                        is ScanResult.Error -> Color(0xFFFF5252)
                        is ScanResult.Offline -> Color(0xFF9E9E9E)
                    },
                    shape = goTickyShapes.extraLarge
                )
                .clickable { onDismiss() }
                .padding(24.dp)
        ) {
            when (result) {
                is ScanResult.Success -> SuccessContent(result)
                is ScanResult.AlreadyScanned -> AlreadyScannedContent(result)
                is ScanResult.Error -> ErrorContent(result)
                is ScanResult.Offline -> OfflineContent(result)
            }
        }
    }
}

@Composable
private fun SuccessContent(result: ScanResult.Success) {
    var checkmarkVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(100)
        checkmarkVisible = true
    }
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Animated checkmark
        AnimatedVisibility(
            visible = checkmarkVisible,
            enter = scaleIn(
                initialScale = 0f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ) + fadeIn()
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f))
                    .border(3.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Success",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
        
        Text(
            text = "✓ Ticket Scanned Successfully",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.3f),
                    blurRadius = 8f
                )
            ),
            color = Color.White,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Ticket details
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(goTickyShapes.medium)
                .background(Color.White.copy(alpha = 0.15f))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DetailRow("Ticket ID", result.ticket.id)
            DetailRow("Holder", result.ticket.holderName)
            DetailRow("Event", result.ticket.eventTitle)
            DetailRow("Seat", result.ticket.seat)
            DetailRow("Type", result.ticket.type.name)
        }
        
        Text(
            text = "Tap to dismiss",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun AlreadyScannedContent(result: ScanResult.AlreadyScanned) {
    val infiniteTransition = rememberInfiniteTransition(label = "warningPulse")
    val warningScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "warningScale"
    )
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Warning icon
        Box(
            modifier = Modifier
                .size(80.dp)
                .scale(warningScale)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.2f))
                .border(3.dp, Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Warning,
                contentDescription = "Already Scanned",
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
        }
        
        Text(
            text = "⚠ Already Scanned",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.3f),
                    blurRadius = 8f
                )
            ),
            color = Color.White,
            textAlign = TextAlign.Center
        )
        
        Text(
            text = "This ticket was already checked in",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = 0.9f),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Original scan info
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(goTickyShapes.medium)
                .background(Color.White.copy(alpha = 0.15f))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DetailRow("Ticket ID", result.ticket.id)
            DetailRow("Holder", result.ticket.holderName)
            result.originalScanTime?.let {
                DetailRow("Scanned At", it)
            }
            result.originalScanner?.let {
                DetailRow("Scanned By", it)
            }
        }
        
        Text(
            text = "Tap to dismiss",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun ErrorContent(result: ScanResult.Error) {
    val infiniteTransition = rememberInfiniteTransition(label = "errorShake")
    val errorRotation by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "errorRotation"
    )
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Error icon
        Box(
            modifier = Modifier
                .size(80.dp)
                .rotate(errorRotation)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.2f))
                .border(3.dp, Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Cancel,
                contentDescription = "Error",
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
        }
        
        Text(
            text = "✗ Scan Failed",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.3f),
                    blurRadius = 8f
                )
            ),
            color = Color.White,
            textAlign = TextAlign.Center
        )
        
        Text(
            text = result.message,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = 0.9f),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Error details
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(goTickyShapes.medium)
                .background(Color.White.copy(alpha = 0.15f))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DetailRow("Error Code", result.errorCode.name)
            DetailRow("Action", when (result.errorCode) {
                org.example.project.data.ScanErrorCode.TICKET_NOT_FOUND -> "Verify ticket ID"
                org.example.project.data.ScanErrorCode.INVALID_STATUS -> "Check ticket status"
                org.example.project.data.ScanErrorCode.EXPIRED_TICKET -> "Ticket has expired"
                org.example.project.data.ScanErrorCode.NETWORK_ERROR -> "Check internet connection"
                org.example.project.data.ScanErrorCode.PERMISSION_DENIED -> "Contact administrator"
                org.example.project.data.ScanErrorCode.INVALID_QR_DATA -> "Scan again"
                org.example.project.data.ScanErrorCode.DUPLICATE_SCAN -> "Already checked in"
                org.example.project.data.ScanErrorCode.UNKNOWN_ERROR -> "Try again"
            })
        }
        
        Text(
            text = "Tap to dismiss",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun OfflineContent(result: ScanResult.Offline) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Offline icon
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.2f))
                .border(3.dp, Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.CloudOff,
                contentDescription = "Offline",
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
        }
        
        Text(
            text = "📴 Queued for Sync",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.3f),
                    blurRadius = 8f
                )
            ),
            color = Color.White,
            textAlign = TextAlign.Center
        )
        
        Text(
            text = if (result.queuedForSync) {
                "Scan saved offline. Will sync when online."
            } else {
                "Offline mode active. Scan recorded locally."
            },
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = 0.9f),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Ticket details
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(goTickyShapes.medium)
                .background(Color.White.copy(alpha = 0.15f))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DetailRow("Ticket ID", result.ticket.id)
            DetailRow("Holder", result.ticket.holderName)
            DetailRow("Status", "Pending sync")
        }
        
        Text(
            text = "Tap to dismiss",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = Color.White.copy(alpha = 0.8f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f, fill = false)
        )
    }
}
