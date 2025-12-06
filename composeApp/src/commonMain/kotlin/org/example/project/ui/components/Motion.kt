package org.example.project.ui.components

import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateFloat
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

@Stable
object GoTickyMotion {
    const val Quick = 120
    const val Standard = 220
    const val Comfort = 320
    const val Emphasized = 420
}

@Composable
fun Modifier.pressAnimated(scaleDown: Float = 0.94f): Modifier {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) scaleDown else 1f,
        animationSpec = tween(GoTickyMotion.Standard, easing = EaseOutBack),
        label = "pressScale"
    )
    return this
        .graphicsLayer(scaleX = scale, scaleY = scale)
        .then(Modifier)
        .also { /* consume interaction source in callers */ }
}

@Composable
fun rememberPressInteractionSource(): MutableInteractionSource = remember { MutableInteractionSource() }

@Composable
fun infinitePulseAmplitude(
    minScale: Float = 0.97f,
    maxScale: Float = 1.03f,
    durationMillis: Int = 2600,
): Float {
    val transition = rememberInfiniteTransition(label = "pulse")
    val pulse by transition.animateFloat(
        initialValue = minScale,
        targetValue = maxScale,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = LinearEasing)
        ),
        label = "pulseAnim"
    )
    return pulse
}
