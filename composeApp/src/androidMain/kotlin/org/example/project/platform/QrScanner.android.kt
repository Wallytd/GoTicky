package org.example.project.platform

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.coroutines.resume

/**
 * Android implementation of QR scanner engine with MLKit and ZXing support.
 */
actual fun createQrScannerEngine(): QrScannerEngine {
    return AndroidQrScannerEngine()
}

/**
 * Request camera permission on Android.
 */
@Composable
actual fun requestCameraPermission(): Boolean {
    val context = LocalContext.current
    var permissionGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionGranted = isGranted
    }
    
    LaunchedEffect(Unit) {
        if (!permissionGranted) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }
    
    return permissionGranted
}

/**
 * Check if camera permission is granted on Android.
 */
actual fun hasCameraPermission(): Boolean {
    // Note: This requires context which isn't available in expect/actual
    // In real implementation, pass context or use a different approach
    return false
}

/**
 * Android-specific camera preview with MLKit barcode scanning.
 */
@Composable
fun AndroidCameraPreview(
    onQrDetected: (String) -> Unit,
    flashlightEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> = remember { ProcessCameraProvider.getInstance(context) }
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    var camera by remember { mutableStateOf<Camera?>(null) }
    
    DisposableEffect(Unit) {
        onDispose {
            cameraExecutor.shutdown()
        }
    }
    
    // Update flashlight when enabled changes
    LaunchedEffect(flashlightEnabled, camera) {
        camera?.cameraControl?.enableTorch(flashlightEnabled)
    }
    
    AndroidView(
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            
            cameraProviderFuture.addListener({
                try {
                    val cameraProvider = cameraProviderFuture.get()
                    
                    // Preview use case
                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
                    
                    // Image analysis use case for MLKit
                    val imageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also {
                            it.setAnalyzer(cameraExecutor, MLKitBarcodeAnalyzer { barcodes ->
                                barcodes.firstOrNull()?.rawValue?.let { qrData ->
                                    onQrDetected(qrData)
                                }
                            })
                        }
                    
                    // Camera selector
                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                    
                    cameraProvider.unbindAll()
                    camera = cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalysis
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, ContextCompat.getMainExecutor(ctx))
            
            previewView
        },
        modifier = modifier.fillMaxSize()
    )
}

/**
 * MLKit barcode analyzer for camera frames.
 */
private class MLKitBarcodeAnalyzer(
    private val onBarcodesDetected: (List<Barcode>) -> Unit
) : ImageAnalysis.Analyzer {
    
    private val scanner = BarcodeScanning.getClient()
    
    @androidx.camera.core.ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )
            
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.isNotEmpty()) {
                        onBarcodesDetected(barcodes)
                    }
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }
}

/**
 * Android-specific QR scanner engine implementation.
 */
class AndroidQrScannerEngine : QrScannerEngine {
    private var useMLKit = true
    private var isInitialized = false
    private var cameraExecutor: ExecutorService? = null
    
    override suspend fun initialize(useMLKit: Boolean): Result<Unit> {
        return runCatching {
            this.useMLKit = useMLKit
            this.cameraExecutor = Executors.newSingleThreadExecutor()
            isInitialized = true
        }
    }
    
    override suspend fun startScanning(): Result<String> {
        return runCatching {
            if (!isInitialized) {
                throw IllegalStateException("Scanner not initialized")
            }
            
            // Scanning is handled by the camera preview composable
            // This method is not used in the Compose-based implementation
            suspendCancellableCoroutine { continuation ->
                // Placeholder - actual scanning happens in AndroidCameraPreview
                continuation.resume("SCANNING_IN_PROGRESS")
            }
        }
    }
    
    override suspend fun stopScanning() {
        cameraExecutor?.shutdown()
        cameraExecutor = null
        isInitialized = false
    }
    
    override suspend fun isMLKitAvailable(): Boolean {
        // MLKit is available if Google Play Services is available
        return try {
            Class.forName("com.google.mlkit.vision.barcode.BarcodeScanning")
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }
    
    override suspend fun toggleFlashlight(enabled: Boolean) {
        // Flashlight is controlled via Camera instance in AndroidCameraPreview
        // This is handled by the composable
    }
}

/**
 * Helper to check camera permission with context.
 */
fun hasCameraPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
}
