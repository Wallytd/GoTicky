package org.example.project.platform

import androidx.compose.runtime.Composable

@Composable
actual fun SystemBackHandler(enabled: Boolean, onBack: () -> Unit) {
    // Desktop build: no system back concept; swallow to keep API parity.
    if (enabled) {
        // no-op
    }
}
