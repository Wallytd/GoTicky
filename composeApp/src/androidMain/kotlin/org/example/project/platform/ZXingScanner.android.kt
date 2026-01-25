package org.example.project.platform

/**
 * ZXing-based barcode scanner for offline fallback.
 * This provides a lightweight alternative when MLKit is unavailable.
 */

import android.graphics.ImageFormat
import androidx.camera.core.Camera
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import java.nio.ByteBuffer
import java.util.concurrent.Executors

/**
 * ZXing barcode analyzer for camera frames.
 * Used as fallback when MLKit is unavailable or offline mode is enabled.
 */
class ZXingBarcodeAnalyzer(
    private val onBarcodeDetected: (String) -> Unit
) : ImageAnalysis.Analyzer {
    
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
                // No barcode found in this frame
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
@Composable
fun AndroidCameraPreviewZXing(
    onQrDetected: (String) -> Unit,
    flashlightEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }
    val cameraExecutor = remember {
        Executors.newSingleThreadExecutor()
    }
    val camera = remember {
        mutableStateOf<Camera?>(null)
    }
    
    DisposableEffect(Unit) {
        onDispose {
            cameraExecutor.shutdown()
        }
    }
    
    // Update flashlight
    LaunchedEffect(flashlightEnabled, camera.value) {
        camera.value?.cameraControl?.enableTorch(flashlightEnabled)
    }
    
    AndroidView(
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            
            cameraProviderFuture.addListener({
                try {
                    val cameraProvider = cameraProviderFuture.get()
                    
                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
                    
                    val imageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also {
                            it.setAnalyzer(cameraExecutor, ZXingBarcodeAnalyzer { qrData ->
                                onQrDetected(qrData)
                            })
                        }
                    
                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                    
                    cameraProvider.unbindAll()
                    camera.value = cameraProvider.bindToLifecycle(
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
