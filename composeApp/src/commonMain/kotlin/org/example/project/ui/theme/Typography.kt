package org.example.project.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

// Recommended pairing: Sora (headlines) + Space Grotesk (body/labels). Once font files are provided,
// replace SansSerif with FontFamily(Font(...)) assets. Letter spacing slightly tightened for a premium feel.
private val headlineFamily = FontFamily.SansSerif
private val bodyFamily = FontFamily.SansSerif

private fun baseStyle(
    family: FontFamily,
    weight: FontWeight,
    size: Int,
    line: Int,
    letterSpacing: Float = -0.2f
) = TextStyle(
    fontFamily = family,
    fontWeight = weight,
    fontSize = size.sp,
    lineHeight = line.sp,
    letterSpacing = letterSpacing.sp,
    lineBreak = LineBreak.Heading
)

val goTickyTypography = Typography(
    displayLarge = baseStyle(headlineFamily, FontWeight.Black, 40, 46, -0.8f),
    displayMedium = baseStyle(headlineFamily, FontWeight.ExtraBold, 34, 40, -0.6f),
    headlineLarge = baseStyle(headlineFamily, FontWeight.Bold, 28, 34, -0.4f),
    headlineMedium = baseStyle(headlineFamily, FontWeight.SemiBold, 24, 30, -0.3f),
    titleLarge = baseStyle(headlineFamily, FontWeight.SemiBold, 20, 26, -0.1f),
    titleMedium = baseStyle(bodyFamily, FontWeight.Medium, 16, 22, 0f),
    titleSmall = baseStyle(bodyFamily, FontWeight.Medium, 14, 20, 0f),
    bodyLarge = baseStyle(bodyFamily, FontWeight.Normal, 16, 22, 0f),
    bodyMedium = baseStyle(bodyFamily, FontWeight.Normal, 14, 20, 0f),
    bodySmall = baseStyle(bodyFamily, FontWeight.Normal, 12, 16, 0f),
    labelLarge = baseStyle(bodyFamily, FontWeight.SemiBold, 14, 16, 0f),
    labelMedium = baseStyle(bodyFamily, FontWeight.Medium, 12, 14, 0f).copy(textDecoration = TextDecoration.None),
    labelSmall = baseStyle(bodyFamily, FontWeight.Medium, 11, 13, 0f)
)
