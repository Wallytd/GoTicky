package org.example.project.platform

import androidx.compose.runtime.Composable

@Composable
actual fun SystemBackHandler(enabled: Boolean, onBack: () -> Unit) {
    // Web/JS: no native back gesture; swallow for parity.
    if (enabled) {
        // no-op
    }
}
