package org.example.project.platform

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.toComposeImageBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.ByteArrayOutputStream
import org.jetbrains.skia.Image

actual fun platformQrBitmap(seed: String, sizePx: Int, dark: Color, light: Color): ImageBitmap {
    val writer = MultiFormatWriter()
    val hints = mapOf(EncodeHintType.MARGIN to 0)
    val matrix: BitMatrix = writer.encode(seed.ifBlank { "qr-$sizePx" }, BarcodeFormat.QR_CODE, sizePx, sizePx, hints)
    return matrix.toImageBitmap(dark, light)
}

actual fun platformBarcodeBitmap(seed: String, width: Int, height: Int, dark: Color, light: Color): ImageBitmap {
    val writer = MultiFormatWriter()
    val hints = mapOf(EncodeHintType.MARGIN to 0)
    val matrix: BitMatrix = writer.encode(seed.ifBlank { "barcode-$width" }, BarcodeFormat.CODE_128, width, height, hints)
    return matrix.toImageBitmap(dark, light)
}

private fun BitMatrix.toImageBitmap(dark: Color, light: Color): ImageBitmap {
    val img = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val darkInt = dark.toArgb()
    val lightInt = light.toArgb()
    for (x in 0 until width) {
        for (y in 0 until height) {
            img.setRGB(x, y, if (this[x, y]) darkInt else lightInt)
        }
    }
    val baos = ByteArrayOutputStream()
    ImageIO.write(img, "png", baos)
    val skia = Image.makeFromEncoded(baos.toByteArray())
    return skia.toComposeImageBitmap()
}
