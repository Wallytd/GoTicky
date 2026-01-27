@file:kotlin.jvm.JvmName("AndroidQrGeneratorsKt")
package org.example.project.platform

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix

actual fun platformQrBitmap(seed: String, sizePx: Int, dark: Color, light: Color): ImageBitmap {
    val writer = MultiFormatWriter()
    val hints = mapOf(EncodeHintType.MARGIN to 0)
    val matrix: BitMatrix = writer.encode(seed.ifBlank { "qr-$sizePx" }, BarcodeFormat.QR_CODE, sizePx, sizePx, hints)
    return bitMatrixToImageBitmap(matrix, dark, light)
}

actual fun platformBarcodeBitmap(seed: String, width: Int, height: Int, dark: Color, light: Color): ImageBitmap {
    val writer = MultiFormatWriter()
    val hints = mapOf(EncodeHintType.MARGIN to 0)
    val matrix: BitMatrix = writer.encode(seed.ifBlank { "barcode-$width" }, BarcodeFormat.CODE_128, width, height, hints)
    return bitMatrixToImageBitmap(matrix, dark, light)
}

private fun bitMatrixToImageBitmap(matrix: BitMatrix, dark: Color, light: Color): ImageBitmap {
    val bmp = Bitmap.createBitmap(matrix.width, matrix.height, Bitmap.Config.ARGB_8888)
    val darkInt = dark.toArgb()
    val lightInt = light.toArgb()
    for (x in 0 until matrix.width) {
        for (y in 0 until matrix.height) {
            bmp.setPixel(x, y, if (matrix[x, y]) darkInt else lightInt)
        }
    }
    return bmp.asImageBitmap()
}
