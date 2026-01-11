package org.example.project.platform

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap

// JS/web stub: return a transparent bitmap sized to requested dimensions.
private fun blankBitmap(width: Int, height: Int): ImageBitmap = ImageBitmap(width, height)

actual fun platformQrBitmap(seed: String, sizePx: Int, dark: Color, light: Color): ImageBitmap =
    blankBitmap(sizePx, sizePx)

actual fun platformBarcodeBitmap(seed: String, width: Int, height: Int, dark: Color, light: Color): ImageBitmap =
    blankBitmap(width, height)
