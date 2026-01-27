package org.example.project.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Stage 9: richer visual tokens (palette, gradients, texture/halo cues) driven by the GoTicky web art direction.
@Suppress("unused")
private object BrandPalette {
    // Core neon hero + CTA (find events / sign up)
    val neonPrimary = Color(0xFFD6FF1F)
    val neonPrimaryGlow = Color(0xFFE4FF6B)
    val onNeonPrimary = Color(0xFF0A0C12)

    // Dark surfaces
    val background = Color(0xFF05070D)
    val surface = Color(0xFF0B1020)
    val surfaceElevated = Color(0xFF111A2C)
    val surfaceGlass = Color(0x1AF4F7FF) // translucent glass overlay
    val outline = Color(0xFF1F2A3E)

    // Accents inspired by nav chips (subtle distinct colors per icon category)
    val royalBlue = Color(0xFF2C4BFF)
    val violet = Color(0xFF7C5BFF)
    val magenta = Color(0xFFFF5C8A)
    val teal = Color(0xFF20E0B4)
    val amber = Color(0xFFFFC94A)
    val mint = Color(0xFFB3FFCE)

    // Semantic
    val success = Color(0xFF4BE38D)
    val warning = amber
    val error = magenta

    // Text
    val onSurface = Color(0xFFE8ECF5)
    val onSurfaceMedium = Color(0xFFB7BED2)
}

val IconCategoryColors: Map<IconCategory, Color> = mapOf(
    IconCategory.Discover to BrandPalette.neonPrimary,
    IconCategory.Calendar to BrandPalette.violet,
    IconCategory.Map to BrandPalette.teal,
    IconCategory.Ticket to BrandPalette.royalBlue,
    IconCategory.Wallet to BrandPalette.amber,
    IconCategory.Alerts to BrandPalette.warning,
    IconCategory.Profile to BrandPalette.mint,
    IconCategory.Admin to Color(0xFF56D4FF),
)

enum class IconCategory {
    Discover, Calendar, Map, Ticket, Wallet, Alerts, Profile, Admin
}

object GoTickyGradients {
    // Hero splash + CTA duotone
    val Hero = Brush.linearGradient(
        colors = listOf(BrandPalette.neonPrimaryGlow, BrandPalette.neonPrimary, BrandPalette.royalBlue)
    )
    // Primary CTA sheen
    val Cta = Brush.horizontalGradient(
        colors = listOf(BrandPalette.neonPrimary, BrandPalette.violet)
    )
    // Card depth
    val CardGlow = Brush.verticalGradient(
        colors = listOf(BrandPalette.surfaceElevated, BrandPalette.surface)
    )
    // Glass/overlay wash for banners or elevated surfaces
    val GlassWash = Brush.linearGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.06f),
            Color.White.copy(alpha = 0.02f),
            Color.Transparent
        )
    )
    // Neon edge/halo for hover/press accents
    val EdgeHalo = Brush.horizontalGradient(
        colors = listOf(
            BrandPalette.neonPrimary.copy(alpha = 0.65f),
            BrandPalette.violet.copy(alpha = 0.55f),
            BrandPalette.royalBlue.copy(alpha = 0.65f)
        )
    )
}

@Suppress("unused")
object GoTickyTextures {
    // Subtle grain tint to layer on dark glass; use with low alpha drawBehind.
    val GrainTint: Color = Color.White.copy(alpha = 0.035f)
    // Soft shadow spot color for elevated cards.
    val ShadowSpot: Color = Color(0x44000000)
}

fun goTickyDarkColors(): ColorScheme = darkColorScheme(
    primary = BrandPalette.neonPrimary,
    onPrimary = BrandPalette.onNeonPrimary,
    primaryContainer = BrandPalette.neonPrimaryGlow,
    onPrimaryContainer = BrandPalette.onNeonPrimary,
    secondary = BrandPalette.royalBlue,
    onSecondary = BrandPalette.onSurface,
    tertiary = BrandPalette.teal,
    onTertiary = BrandPalette.onSurface,
    background = BrandPalette.background,
    onBackground = BrandPalette.onSurface,
    surface = BrandPalette.surface,
    onSurface = BrandPalette.onSurface,
    surfaceVariant = BrandPalette.surfaceElevated,
    onSurfaceVariant = BrandPalette.onSurfaceMedium,
    outline = BrandPalette.outline,
    error = BrandPalette.error,
    onError = Color.White,
)

@Suppress("unused")
fun goTickyLightColors(): ColorScheme = lightColorScheme(
    primary = BrandPalette.neonPrimary,
    onPrimary = BrandPalette.onNeonPrimary,
    primaryContainer = BrandPalette.neonPrimaryGlow,
    onPrimaryContainer = BrandPalette.onNeonPrimary,
    secondary = BrandPalette.royalBlue,
    onSecondary = Color.White,
    tertiary = BrandPalette.teal,
    onTertiary = Color(0xFF002018),
    background = Color(0xFFF8FAFF),
    onBackground = Color(0xFF0A0C12),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF0A0C12),
    surfaceVariant = Color(0xFFE6E9F4),
    onSurfaceVariant = Color(0xFF2D3446),
    outline = Color(0xFF55607C),
    error = BrandPalette.error,
    onError = Color.White,
)
