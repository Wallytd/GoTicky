package org.example.project.platform

import androidx.compose.runtime.Composable

@Composable
actual fun rememberBiometricLauncher(): BiometricLauncher =
    object : BiometricLauncher {
        override suspend fun authenticate(config: BiometricPromptConfig): BiometricResult =
            BiometricResult.NotAvailable
    }
