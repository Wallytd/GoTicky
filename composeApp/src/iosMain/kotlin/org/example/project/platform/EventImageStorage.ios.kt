package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

/**
 * Lightweight iOS placeholder that echoes the provided URI and reports instant completion.
 * This keeps shared flows (organizer flyer uploads) working in previews/simulator while
 * avoiding a hard dependency on Firebase Storage for iOS.
 */
private class IosEventImageStorage : EventImageStorage {
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
    remember { IosEventImageStorage() }
