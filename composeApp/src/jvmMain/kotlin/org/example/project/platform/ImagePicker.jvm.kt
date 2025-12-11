package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter

@Composable
actual fun rememberImagePicker(onImagePicked: (String?) -> Unit): ImagePickerLauncher {
    return remember {
        object : ImagePickerLauncher {
            override fun pickFromGallery() {
                // Desktop stub
            }

            override fun capturePhoto() {
                // Desktop stub
            }
        }
    }
}

@Composable
actual fun rememberUriPainter(uri: String): Painter? = null
