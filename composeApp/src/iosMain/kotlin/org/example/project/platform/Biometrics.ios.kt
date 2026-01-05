@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSError
import platform.LocalAuthentication.LAContext
import platform.LocalAuthentication.LAPolicyDeviceOwnerAuthenticationWithBiometrics
import kotlin.coroutines.resume

@Composable
actual fun rememberBiometricLauncher(): BiometricLauncher =
    remember {
        object : BiometricLauncher {
            override suspend fun authenticate(config: BiometricPromptConfig): BiometricResult {
                val context = LAContext()
                val canEvaluate = context.canEvaluatePolicy(LAPolicyDeviceOwnerAuthenticationWithBiometrics, null)
                if (!canEvaluate) return BiometricResult.NotAvailable

                return suspendCancellableCoroutine { cont ->
                    context.evaluatePolicy(
                        policy = LAPolicyDeviceOwnerAuthenticationWithBiometrics,
                        localizedReason = config.description ?: config.title,
                        reply = { success: Boolean, error: NSError? ->
                            val result = when {
                                success -> BiometricResult.Success
                                error != null -> BiometricResult.Error(error.localizedDescription ?: "Biometric failed")
                                else -> BiometricResult.Error("Biometric failed")
                            }
                            if (cont.isActive) cont.resume(result)
                        }
                    )
                    cont.invokeOnCancellation {
                        context.invalidate()
                    }
                }
            }
        }
    }
