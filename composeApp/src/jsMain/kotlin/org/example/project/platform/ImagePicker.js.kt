package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.khronos.webgl.Int8Array
import kotlinx.browser.document
import kotlinx.coroutines.launch
import kotlinx.coroutines.await
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.url.URL
import org.jetbrains.skia.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener

private class JsImagePicker(
    private val onImagePicked: (String?) -> Unit
) : ImagePickerLauncher {
    
    private val input = document.createElement("input") as HTMLInputElement

    init {
        input.type = "file"
        input.accept = "image/*"
        input.style.display = "none"
        document.body?.appendChild(input)

        input.addEventListener("change", object : EventListener {
            override fun handleEvent(event: Event) {
                val file = input.files?.item(0)
                if (file != null) {
                    val url = URL.createObjectURL(file)
                    onImagePicked(url)
                } else {
                    onImagePicked(null)
                }
            }
        })
    }

    override fun pickFromGallery() {
        input.click()
    }

    override fun capturePhoto() {
        // Browser doesn't support direct intent for camera only, 
        // but 'accept="image/*;capture=camera"' could trigger it on mobile web.
        // For now, reuse gallery picker logic which usually offers camera option on mobile.
        input.click()
    }
}

@Composable
actual fun rememberImagePicker(onImagePicked: (String?) -> Unit): ImagePickerLauncher {
    return remember { JsImagePicker(onImagePicked) }
}

@Composable
actual fun rememberUriPainter(uri: String): Painter? {
    var painter by remember(uri) { mutableStateOf<Painter?>(null) }
    
    LaunchedEffect(uri) {
        if (uri.isNotEmpty()) {
            try {
                // Fetch the image as blob/bytes and convert to Bitmap
                // This is a simplified approach using Skia
                val response = kotlinx.browser.window.fetch(uri).await()
                val arrayBuffer = response.arrayBuffer().await()
                val bytes = Int8Array(arrayBuffer).unsafeCast<ByteArray>()
                val image = Image.makeFromEncoded(bytes)
                painter = BitmapPainter(image.toComposeImageBitmap())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    return painter
}

// Helper for Int8Array to ByteArray
private fun Int8Array.unsafeCast(): ByteArray = this.asDynamic()
