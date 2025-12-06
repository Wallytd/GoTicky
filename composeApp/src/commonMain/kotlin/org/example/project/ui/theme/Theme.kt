package org.example.project.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun GoTickyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) goTickyDarkColors() else goTickyLightColors()
    MaterialTheme(
        colorScheme = colors,
        typography = goTickyTypography,
        shapes = goTickyShapes,
        content = content,
    )
}
