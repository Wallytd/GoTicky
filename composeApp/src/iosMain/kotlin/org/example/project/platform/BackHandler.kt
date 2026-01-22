package org.example.project.platform

import androidx.compose.runtime.Composable

@Composable
actual fun SystemBackHandler(enabled: Boolean, onBack: () -> Unit) {
    // iOS has no system back button in Compose embedding; no-op to keep parity.
    if (enabled) {
        // no-op
    }
}
