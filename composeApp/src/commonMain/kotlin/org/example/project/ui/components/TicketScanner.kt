package org.example.project.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.data.*
import org.example.project.platform.*
import org.example.project.ui.theme.GoTickyGradients
import org.example.project.ui.theme.GoTickyTextures
import org.example.project.ui.theme.goTickyShapes

/**
 * Modern ticket scanner with hybrid MLKit/ZXing support and offline capabilities.
 */
@Composable
fun TicketScanner(
    scannerConfig: ScannerConfig = ScannerConfig(),
    onScanResult: (ScanResult) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    var scannerEngine by remember { mutableStateOf<ScannerEngine>(scannerConfig.preferredEngine) }
    var isScanning by remember { mutableStateOf(false) }
    var flashlightEnabled by remember { mutableStateOf(scannerConfig.enableFlashlight) }
    var scanResult by remember { mutableStateOf<ScanResult?>(null) }
    var showResult by remember { mutableStateOf(false) }
    
    val scope = rememberCoroutineScope()
    val ticketRepository = remember { TicketRepository() }
    val scanProcessor = remember { ScanProcessor(ticketRepository) }
    
    // Request permission on first launch
    val hasPermission = requestCameraPermission()
    
    // Auto-select scanner engine based on connectivity
    LaunchedEffect(scannerConfig.offlineMode) {
        scannerEngine = if (scannerConfig.offlineMode) {
            ScannerEngine.ZXING
        } else {
            scanProcessor.getRecommendedEngine(
                hasInternet = isFirebaseAvailable()
            )
        }
    }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (!hasPermission) {
            // Permission rationale screen
            PermissionRationaleScreen(
                onRequestPermission = { },
                onClose = onClose
            )
        } else {
            // Scanner UI
            Box(modifier = Modifier.fillMaxSize()) {
                // Camera preview (platform-specific)
                CameraPreview(
                    modifier = Modifier.fillMaxSize(),
                    flashlightEnabled = flashlightEnabled,
                    onQrDetected = { qrData ->
                        if (!isScanning) {
                            isScanning = true
                            scope.launch {
                                val result = scanProcessor.processScan(
                                    qrData = qrData,
                                    scannerId = "admin-scanner-1", // TODO: Get from auth
                                    scannerName = "Admin Scanner",
                                    scannerEngine = scannerEngine,
                                    offlineMode = scannerConfig.offlineMode
                                )
                                
                                scanResult = result
                                showResult = true
                                onScanResult(result)
                                
                                // Auto-dismiss after delay
                                delay(3000)
                                showResult = false
                                isScanning = false
                            }
                        }
                    }
                )
                
                // Scanner overlay with guide
                ScannerOverlay(
                    modifier = Modifier.fillMaxSize(),
                    isScanning = isScanning
                )
                
                // Top controls
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.TopCenter),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Close button
                    IconButton(
                        onClick = onClose,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.5f))
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Close",
                            tint = Color.White
                        )
                    }
                    
                    // Scanner engine indicator
                    Pill(
                        text = when (scannerEngine) {
                            ScannerEngine.MLKIT -> "MLKit (Online)"
                            ScannerEngine.ZXING -> "ZXing (Offline)"
                            ScannerEngine.AUTO -> "Auto"
                            ScannerEngine.MANUAL -> "Manual"
                        },
                        color = when (scannerEngine) {
                            ScannerEngine.MLKIT -> Color(0xFF4CAF50)
                            ScannerEngine.ZXING -> Color(0xFFFF9800)
                            else -> Color.Gray
                        }.copy(alpha = 0.3f),
                        textColor = Color.White
                    )
                    
                    // Flashlight toggle
                    IconButton(
                        onClick = {
                            flashlightEnabled = !flashlightEnabled
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(
                                if (flashlightEnabled) 
                                    Color(0xFFFFC107).copy(alpha = 0.3f)
                                else 
                                    Color.Black.copy(alpha = 0.5f)
                            )
                    ) {
                        Icon(
                            imageVector = if (flashlightEnabled) 
                                Icons.Outlined.FlashOn 
                            else 
                                Icons.Outlined.FlashOff,
                            contentDescription = "Flashlight",
                            tint = if (flashlightEnabled) Color(0xFFFFC107) else Color.White
                        )
                    }
                }
                
                // Bottom info
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Align QR code or barcode within the frame",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    
                    // Manual entry button
                    GhostButton(
                        text = "Enter ticket ID manually",
                        onClick = {
                            // TODO: Show manual entry dialog
                        },
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )
                }
                
                // Scan result overlay
                AnimatedVisibility(
                    visible = showResult && scanResult != null,
                    enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                    exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    scanResult?.let { result ->
                        ScanResultOverlay(
                            result = result,
                            onDismiss = {
                                showResult = false
                                isScanning = false
                            }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Scanner overlay with animated guide frame.
 */
@Composable
private fun ScannerOverlay(
    isScanning: Boolean,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "scannerPulse")
    val scanLineOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "scanLine"
    )
    
    val cornerPulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "cornerPulse"
    )
    
    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        
        // Darken areas outside the scan frame
        val frameSize = minOf(canvasWidth, canvasHeight) * 0.7f
        val frameLeft = (canvasWidth - frameSize) / 2
        val frameTop = (canvasHeight - frameSize) / 2
        
        // Top overlay
        drawRect(
            color = Color.Black.copy(alpha = 0.6f),
            topLeft = Offset(0f, 0f),
            size = Size(canvasWidth, frameTop)
        )
        
        // Bottom overlay
        drawRect(
            color = Color.Black.copy(alpha = 0.6f),
            topLeft = Offset(0f, frameTop + frameSize),
            size = Size(canvasWidth, canvasHeight - frameTop - frameSize)
        )
        
        // Left overlay
        drawRect(
            color = Color.Black.copy(alpha = 0.6f),
            topLeft = Offset(0f, frameTop),
            size = Size(frameLeft, frameSize)
        )
        
        // Right overlay
        drawRect(
            color = Color.Black.copy(alpha = 0.6f),
            topLeft = Offset(frameLeft + frameSize, frameTop),
            size = Size(canvasWidth - frameLeft - frameSize, frameSize)
        )
        
        // Scan frame border
        val borderColor = if (isScanning) Color(0xFF4CAF50) else Color(0xFF2196F3)
        drawRoundRect(
            color = borderColor,
            topLeft = Offset(frameLeft, frameTop),
            size = Size(frameSize, frameSize),
            cornerRadius = CornerRadius(16f, 16f),
            style = Stroke(width = 4f)
        )
        
        // Corner indicators
        val cornerLength = 40f * cornerPulse
        val cornerWidth = 6f
        
        // Top-left corner
        drawLine(
            color = borderColor,
            start = Offset(frameLeft, frameTop + cornerLength),
            end = Offset(frameLeft, frameTop),
            strokeWidth = cornerWidth
        )
        drawLine(
            color = borderColor,
            start = Offset(frameLeft, frameTop),
            end = Offset(frameLeft + cornerLength, frameTop),
            strokeWidth = cornerWidth
        )
        
        // Top-right corner
        drawLine(
            color = borderColor,
            start = Offset(frameLeft + frameSize - cornerLength, frameTop),
            end = Offset(frameLeft + frameSize, frameTop),
            strokeWidth = cornerWidth
        )
        drawLine(
            color = borderColor,
            start = Offset(frameLeft + frameSize, frameTop),
            end = Offset(frameLeft + frameSize, frameTop + cornerLength),
            strokeWidth = cornerWidth
        )
        
        // Bottom-left corner
        drawLine(
            color = borderColor,
            start = Offset(frameLeft, frameTop + frameSize - cornerLength),
            end = Offset(frameLeft, frameTop + frameSize),
            strokeWidth = cornerWidth
        )
        drawLine(
            color = borderColor,
            start = Offset(frameLeft, frameTop + frameSize),
            end = Offset(frameLeft + cornerLength, frameTop + frameSize),
            strokeWidth = cornerWidth
        )
        
        // Bottom-right corner
        drawLine(
            color = borderColor,
            start = Offset(frameLeft + frameSize - cornerLength, frameTop + frameSize),
            end = Offset(frameLeft + frameSize, frameTop + frameSize),
            strokeWidth = cornerWidth
        )
        drawLine(
            color = borderColor,
            start = Offset(frameLeft + frameSize, frameTop + frameSize - cornerLength),
            end = Offset(frameLeft + frameSize, frameTop + frameSize),
            strokeWidth = cornerWidth
        )
        
        // Animated scan line
        if (isScanning) {
            val scanY = frameTop + (frameSize * scanLineOffset)
            drawLine(
                color = Color(0xFF4CAF50).copy(alpha = 0.7f),
                start = Offset(frameLeft, scanY),
                end = Offset(frameLeft + frameSize, scanY),
                strokeWidth = 3f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
            )
        }
    }
}

/**
 * Camera preview with platform-specific implementation.
 */
@Composable
private fun CameraPreview(
    flashlightEnabled: Boolean,
    onQrDetected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Use platform-specific camera preview
    // On Android, this will use AndroidCameraPreview or AndroidCameraPreviewZXing
    // On other platforms, show placeholder
    Box(
        modifier = modifier.background(Color.DarkGray),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Camera preview - Platform implementation required",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.7f)
        )
    }
}

/**
 * Permission rationale screen.
 */
@Composable
private fun PermissionRationaleScreen(
    onRequestPermission: () -> Unit,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GoTickyGradients.CardGlow),
        contentAlignment = Alignment.Center
    ) {
        GlowCard(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.PhotoCamera,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(64.dp)
                )
                
                Text(
                    text = "Camera Permission Required",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
                
                Text(
                    text = "GoTicky needs camera access to scan ticket QR codes and barcodes. Your privacy is important - we only use the camera for scanning.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                PrimaryButton(
                    text = "Grant Permission",
                    onClick = onRequestPermission,
                    modifier = Modifier.fillMaxWidth()
                )
                
                GhostButton(
                    text = "Cancel",
                    onClick = onClose,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
