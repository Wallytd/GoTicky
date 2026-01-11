package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter

private class JsImagePicker : ImagePickerLauncher {
    override fun pickFromGallery() { /* no-op on JS */ }
    override fun capturePhoto() { /* no-op on JS */ }
}

@Composable
actual fun rememberImagePicker(onImagePicked: (String?) -> Unit): ImagePickerLauncher {
    // JS/web stub: cannot pick images without integrating a web picker. No-op to satisfy build.
    return remember { JsImagePicker() }
}

@Composable
actual fun rememberUriPainter(uri: String): Painter? {
    // JS/web stub: return null; callers should handle null painter gracefully.
    return null
}
