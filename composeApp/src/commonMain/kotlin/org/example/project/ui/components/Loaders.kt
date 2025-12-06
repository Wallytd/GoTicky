package org.example.project.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AnimatedProgressBar(
    progress: Float,
    modifier: Modifier = Modifier
) {
    val clamped = progress.coerceIn(0f, 1f)
    val animatedProgress by animateFloatAsState(
        targetValue = clamped,
        animationSpec = tween(GoTickyMotion.Comfort, easing = LinearEasing),
        label = "progressAnim"
    )
    val shimmerX by shimmerDrift()
    Box(
        modifier
            .height(10.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(
            Modifier
                .fillMaxWidth(animatedProgress)
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary
                        ),
                        start = androidx.compose.ui.geometry.Offset(shimmerX, 0f),
                        end = androidx.compose.ui.geometry.Offset(shimmerX + 200f, 0f)
                    )
                )
        )
    }
}

@Composable
fun LoadingSpinner(
    modifier: Modifier = Modifier,
    size: Int = 48,
    stroke: Float = 4f
) {
    val transition = rememberInfiniteTransition(label = "spinner")
    val angle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = GoTickyMotion.Emphasized, easing = LinearEasing)
        ),
        label = "spinAnim"
    )
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    Canvas(modifier = modifier.size(size.dp)) {
        val sweep = size * 0.6f
        val cx = size.toFloat() / 2f
        val cy = size.toFloat() / 2f
        val radius = size.toFloat() / 2f - stroke
        val startAngle = angle
        val sweepAngle = 270f
        drawArc(
            color = primaryColor,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = Stroke(width = stroke, cap = StrokeCap.Round)
        )
        // glow dots
        val glowRadius = radius - stroke * 2f
        val radians = (startAngle + sweepAngle) * (PI.toFloat() / 180f)
        val dotX = cx + glowRadius * cos(radians)
        val dotY = cy + glowRadius * sin(radians)
        drawCircle(
            color = secondaryColor,
            radius = stroke * 1.6f,
            center = androidx.compose.ui.geometry.Offset(dotX, dotY)
        )
    }
}

@Composable
fun LoadingRow(
    modifier: Modifier = Modifier
): Unit = Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(12.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    LoadingSpinner(size = 32)
    AnimatedProgressBar(progress = 0.35f, modifier = Modifier.weight(1f))
}

@Composable
private fun shimmerDrift(): androidx.compose.runtime.State<Float> {
    val transition = rememberInfiniteTransition(label = "shimmer")
    return transition.animateFloat(
        initialValue = -200f,
        targetValue = 400f,
        animationSpec = infiniteRepeatable(tween(1200, easing = LinearEasing)),
        label = "shimmerAnim"
    )
}
