package org.example.project.platform

import android.graphics.ImageFormat
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import com.google.common.util.concurrent.ListenableFuture
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import java.nio.ByteBuffer

/**
 * ZXing barcode analyzer for camera frames.
 * Used as fallback when MLKit is unavailable or offline mode is enabled.
 */
class ZXingBarcodeAnalyzer(
    private val onBarcodeDetected: (String) -> Unit
) : ImageAnalysis.Analyzer {
    
    // Setup ZXing reader with common formats
    private val reader = MultiFormatReader().apply {
        val hints = mapOf(
            DecodeHintType.POSSIBLE_FORMATS to listOf(
                BarcodeFormat.QR_CODE,
                BarcodeFormat.CODE_128,
                BarcodeFormat.CODE_39,
                BarcodeFormat.EAN_13,
                BarcodeFormat.EAN_8
            ),
            DecodeHintType.TRY_HARDER to true
        )
        setHints(hints)
    }
    
    override fun analyze(imageProxy: ImageProxy) {
        if (imageProxy.format == ImageFormat.YUV_420_888 ||
            imageProxy.format == ImageFormat.YUV_422_888 ||
            imageProxy.format == ImageFormat.YUV_444_888
        ) {
            val byteBuffer = imageProxy.planes[0].buffer
            val data = byteBuffer.toByteArray()
            
            // ZXing needs luminance source
            val source = PlanarYUVLuminanceSource(
                data,
                imageProxy.width,
                imageProxy.height,
                0,
                0,
                imageProxy.width,
                imageProxy.height,
                false
            )
            
            val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
            
            try {
                val result = reader.decode(binaryBitmap)
                result.text?.let { qrData ->
                    onBarcodeDetected(qrData)
                }
            } catch (e: NotFoundException) {
                // No barcode found
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        
        imageProxy.close()
    }
    
    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()
        val data = ByteArray(remaining())
        get(data)
        return data
    }
}

/**
 * Android camera preview with ZXing barcode scanning (offline fallback).
 */
@androidx.compose.runtime.Composable
fun AndroidCameraPreviewZXing(
    onQrDetected: (String) -> Unit,
    flashlightEnabled: Boolean,
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current
    val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> = androidx.compose.runtime.remember {
        ProcessCameraProvider.getInstance(context)
    }
    val cameraExecutor = androidx.compose.runtime.remember {
        java.util.concurrent.Executors.newSingleThreadExecutor()
    }
    var camera by androidx.compose.runtime.remember {
        androidx.compose.runtime.mutableStateOf<androidx.camera.core.Camera?>(null)
    }
    
    androidx.compose.runtime.DisposableEffect(Unit) {
        onDispose {
            cameraExecutor.shutdown()
        }
    }
    
    // Update flashlight
    androidx.compose.runtime.LaunchedEffect(flashlightEnabled, camera) {
        camera?.cameraControl?.enableTorch(flashlightEnabled)
    }
    
    androidx.compose.ui.viewinterop.AndroidView(
        factory = { ctx ->
            val previewView = androidx.camera.view.PreviewView(ctx)
            
            cameraProviderFuture.addListener({
                try {
                    val cameraProvider = cameraProviderFuture.get()
                    
                    val preview = androidx.camera.core.Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
                    
                    val imageAnalysis = androidx.camera.core.ImageAnalysis.Builder()
                        .setBackpressureStrategy(androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also {
                            it.setAnalyzer(cameraExecutor, ZXingBarcodeAnalyzer { qrData ->
                                onQrDetected(qrData)
                            })
                        }
                    
                    val cameraSelector = androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA
                    
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
            }, androidx.core.content.ContextCompat.getMainExecutor(ctx))
            
            previewView
        },
        modifier = modifier
    )
}
