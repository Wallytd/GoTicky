package org.example.project.platform

import androidx.compose.runtime.Composable

/**
 * Cross-platform hook for handling system back actions. On platforms without a concept of back
 * navigation, this is a no-op. On Android it delegates to androidx.activity.compose.BackHandler.
 */
@Composable
expect fun SystemBackHandler(enabled: Boolean = true, onBack: () -> Unit)
