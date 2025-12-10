package org.example.project.platform

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap

// ZXing-backed implementations live in androidMain and jvmMain.
// These expects allow common UI code to call platformQrBitmap/platformBarcodeBitmap.
expect fun platformQrBitmap(seed: String, sizePx: Int, dark: Color, light: Color): ImageBitmap
expect fun platformBarcodeBitmap(seed: String, width: Int, height: Int, dark: Color, light: Color): ImageBitmap
