package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter

@Composable
actual fun rememberImagePicker(onImagePicked: (String?) -> Unit): ImagePickerLauncher {
    // Placeholder for iOS integration; no-op launcher.
    return remember {
        object : ImagePickerLauncher {
            override fun pickFromGallery() { /* TODO: hook to iOS picker */ }
            override fun capturePhoto() { /* TODO: hook to iOS camera */ }
        }
    }
}

@Composable
actual fun rememberUriPainter(uri: String): Painter? = null
