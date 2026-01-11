package org.example.project.platform

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@Composable
actual fun rememberBiometricLauncher(): BiometricLauncher {
    val context = LocalContext.current
    val activity = context.findFragmentActivity()
    return remember(activity) {
        if (activity == null) {
            object : BiometricLauncher {
                override suspend fun authenticate(config: BiometricPromptConfig): BiometricResult = BiometricResult.NotAvailable
            }
        } else {
            val executor = ContextCompat.getMainExecutor(context)
            object : BiometricLauncher {
                override suspend fun authenticate(config: BiometricPromptConfig): BiometricResult =
                    suspendCancellableCoroutine { continuation ->
                        val manager = BiometricManager.from(context)
                        val canAuth = manager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
                        if (canAuth != BiometricManager.BIOMETRIC_SUCCESS) {
                            continuation.resume(BiometricResult.NotAvailable)
                            return@suspendCancellableCoroutine
                        }

                        val promptInfo = BiometricPrompt.PromptInfo.Builder()
                            .setTitle(config.title)
                            .apply {
                                config.subtitle?.let { setSubtitle(it) }
                                config.description?.let { setDescription(it) }
                            }
                            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
                            .setNegativeButtonText(config.negative)
                            .build()

                        val prompt = BiometricPrompt(
                            activity,
                            executor,
                            object : BiometricPrompt.AuthenticationCallback() {
                                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                    super.onAuthenticationSucceeded(result)
                                    if (continuation.isActive) continuation.resume(BiometricResult.Success)
                                }

                                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                                    super.onAuthenticationError(errorCode, errString)
                                    if (continuation.isActive) continuation.resume(
                                        BiometricResult.Error(errString.toString())
                                    )
                                }

                                override fun onAuthenticationFailed() {
                                    super.onAuthenticationFailed()
                                    if (continuation.isActive) continuation.resume(
                                        BiometricResult.Error("Fingerprint not recognized. Try again.")
                                    )
                                }
                            }
                        )

                        prompt.authenticate(promptInfo)

                        continuation.invokeOnCancellation {
                            prompt.cancelAuthentication()
                        }
                    }
            }
        }
    }
}

private tailrec fun Context.findFragmentActivity(): FragmentActivity? =
    when (this) {
        is FragmentActivity -> this
        is android.content.ContextWrapper -> baseContext.findFragmentActivity()
        else -> null
    }
