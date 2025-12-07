package org.example.project.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun GoTickyTheme(
    content: @Composable () -> Unit,
) {
    val colors = goTickyDarkColors()
    MaterialTheme(
        colorScheme = colors,
        typography = goTickyTypography,
        shapes = goTickyShapes,
        content = content,
    )
}
