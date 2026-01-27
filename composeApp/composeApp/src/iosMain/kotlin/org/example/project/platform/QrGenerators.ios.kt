package org.example.project.platform

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint

/**
 * Placeholder QR/barcode renderers for iOS. They generate lightweight, seed-based patterns so
 * ticket screens and receipts render without crashing while we wire a native encoder.
 * Uses simple rect tiling; colors match the requested dark/light inputs.
 */
actual fun platformQrBitmap(seed: String, sizePx: Int, dark: Color, light: Color): ImageBitmap {
    return generatePatternBitmap(
        width = sizePx,
        height = sizePx,
        seed = seed,
        block = 12,
        dark = dark,
        light = light
    )
}

actual fun platformBarcodeBitmap(seed: String, width: Int, height: Int, dark: Color, light: Color): ImageBitmap {
    return generatePatternBitmap(
        width = width,
        height = height,
        seed = seed,
        block = 8,
        dark = dark,
        light = light
    )
}

private fun generatePatternBitmap(
    width: Int,
    height: Int,
    seed: String,
    block: Int,
    dark: Color,
    light: Color
): ImageBitmap {
    val bitmap = ImageBitmap(width, height)
    val canvas = Canvas(bitmap)

    val lightPaint = Paint().apply { color = light }
    val darkPaint = Paint().apply { color = dark }

    // Fill background
    canvas.drawRect(Rect(0f, 0f, width.toFloat(), height.toFloat()), lightPaint)

    // Deterministic pattern based on seed hash; keeps visuals stable per ticket.
    val hash = seed.hashCode().toUInt().toLong()
    var cursor = hash
    val cols = (width / block).coerceAtLeast(1)
    val rows = (height / block).coerceAtLeast(1)

    for (y in 0 until rows) {
        for (x in 0 until cols) {
            cursor = cursor * 1103515245 + 12345
            val on = (cursor ushr 16) % 7 < 3 // ~40% fill density
            if (on) {
                val left = x * block.toFloat()
                val top = y * block.toFloat()
                canvas.drawRect(
                    Rect(
                        left = left,
                        top = top,
                        right = (left + block).coerceAtMost(width.toFloat()),
                        bottom = (top + block).coerceAtMost(height.toFloat())
                    ),
                    darkPaint
                )
            }
        }
    }
    return bitmap
}
