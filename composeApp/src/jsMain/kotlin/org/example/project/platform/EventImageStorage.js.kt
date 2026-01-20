package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

/**
 * Web placeholder that immediately returns the provided URI and marks progress as complete.
 * Keeps shared flows working without requiring Firebase Storage on JS.
 */
private class JsEventImageStorage : EventImageStorage {
    override suspend fun uploadEventFlyer(
        organizerId: String,
        eventId: String,
        localUri: String,
        onProgress: (Float) -> Unit,
    ): String? {
        onProgress(1f)
        return localUri
    }
}

@Composable
actual fun rememberEventImageStorage(): EventImageStorage =
    remember { JsEventImageStorage() }
