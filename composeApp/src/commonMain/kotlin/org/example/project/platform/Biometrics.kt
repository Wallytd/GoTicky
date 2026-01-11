package org.example.project.platform

import androidx.compose.runtime.Composable

data class BiometricPromptConfig(
    val title: String = "Unlock with biometrics",
    val subtitle: String? = null,
    val description: String? = null,
    val negative: String = "Cancel"
)

sealed class BiometricResult {
    object Success : BiometricResult()
    object NotAvailable : BiometricResult()
    data class Error(val message: String) : BiometricResult()
}

interface BiometricLauncher {
    suspend fun authenticate(config: BiometricPromptConfig = BiometricPromptConfig()): BiometricResult
}

@Composable
expect fun rememberBiometricLauncher(): BiometricLauncher
