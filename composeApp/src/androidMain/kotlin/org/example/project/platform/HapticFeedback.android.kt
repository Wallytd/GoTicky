package org.example.project.platform

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

/**
 * Haptic feedback utilities for Android.
 */
object HapticFeedback {
    
    /**
     * Trigger success haptic feedback (short vibration).
     */
    fun success(context: Context) {
        vibrate(context, 100)
    }
    
    /**
     * Trigger error haptic feedback (double vibration).
     */
    fun error(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val vibrator = getVibrator(context)
            val pattern = longArrayOf(0, 50, 50, 50)
            vibrator?.vibrate(VibrationEffect.createWaveform(pattern, -1))
        } else {
            @Suppress("DEPRECATION")
            getVibrator(context)?.vibrate(longArrayOf(0, 50, 50, 50), -1)
        }
    }
    
    /**
     * Trigger warning haptic feedback (medium vibration).
     */
    fun warning(context: Context) {
        vibrate(context, 200)
    }
    
    private fun vibrate(context: Context, duration: Long) {
        val vibrator = getVibrator(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(
                VibrationEffect.createOneShot(
                    duration,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(duration)
        }
    }
    
    private fun getVibrator(context: Context): Vibrator? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            vibratorManager?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }
    }
}

/**
 * Composable wrapper for haptic feedback.
 */
@Composable
fun rememberHapticFeedback(): HapticFeedbackController {
    val context = LocalContext.current
    return remember { HapticFeedbackController(context) }
}

/**
 * Controller for haptic feedback.
 */
class HapticFeedbackController(private val context: Context) {
    fun success() = HapticFeedback.success(context)
    fun error() = HapticFeedback.error(context)
    fun warning() = HapticFeedback.warning(context)
}
