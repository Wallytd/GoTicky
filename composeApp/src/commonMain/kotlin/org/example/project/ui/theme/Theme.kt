package org.example.project.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun GoTickyTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit,
) {
    // Force dark mode across the app; keep the parameter for previews/testing overrides.
    val colors = goTickyDarkColors()
    MaterialTheme(
        colorScheme = colors,
        typography = goTickyTypography,
        shapes = goTickyShapes,
        content = content,
    )
}
