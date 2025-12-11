package org.example.project.platform

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import java.io.OutputStream

/**
 * Simple, platform-specific image picker for Android.
 * Returns a launcher you can invoke to select a single image URI.
 */
@Composable
actual fun rememberImagePicker(onImagePicked: (String?) -> Unit): ImagePickerLauncher {
    val context = LocalContext.current
    val pendingBitmap = remember { mutableStateOf<Bitmap?>(null) }
    val pendingUri = remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        pendingUri.value = uri
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        pendingBitmap.value = bitmap
    }

    LaunchedEffect(pendingUri.value) {
        pendingUri.value?.let { picked ->
            onImagePicked(picked.toString())
            pendingUri.value = null
        }
    }

    LaunchedEffect(pendingBitmap.value) {
        pendingBitmap.value?.let { bmp ->
            val saved = saveBitmapToMediaStore(context, bmp)
            onImagePicked(saved?.toString())
            pendingBitmap.value = null
        }
    }

    return object : ImagePickerLauncher {
        override fun pickFromGallery() {
            galleryLauncher.launch("image/*")
        }

        override fun capturePhoto() {
            cameraLauncher.launch(null)
        }
    }
}

@Composable
actual fun rememberUriPainter(uri: String): Painter? {
    val context = LocalContext.current
    return remember(uri) {
        val parsed = Uri.parse(uri)
        context.contentResolver.openInputStream(parsed)?.use { stream ->
            BitmapFactory.decodeStream(stream)?.let { bmp ->
                BitmapPainter(bmp.asImageBitmap())
            }
        }
    }
}

private fun saveBitmapToMediaStore(context: Context, bitmap: Bitmap): Uri? {
    val resolver = context.contentResolver
    val fileName = "goticky_profile_${System.currentTimeMillis()}.jpg"
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/GoTicky")
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }
    }
    val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues) ?: return null
    var out: OutputStream? = null
    try {
        out = resolver.openOutputStream(uri)
        if (out == null || !bitmap.compress(Bitmap.CompressFormat.JPEG, 92, out)) {
            resolver.delete(uri, null, null)
            return null
        }
    } catch (t: Throwable) {
        resolver.delete(uri, null, null)
        return null
    } finally {
        out?.close()
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        contentValues.clear()
        contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
        resolver.update(uri, contentValues, null, null)
    }
    return uri
}
