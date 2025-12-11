package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

interface ImagePickerLauncher {
    fun pickFromGallery()
    fun capturePhoto()
}

@Composable
expect fun rememberImagePicker(onImagePicked: (String?) -> Unit): ImagePickerLauncher

@Composable
expect fun rememberUriPainter(uri: String): Painter?
