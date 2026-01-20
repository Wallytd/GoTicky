package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

/**
 * Desktop/TV/other JVM placeholder that echoes the provided URI and instantly completes.
 * Keeps shared organizer flyer flows working without requiring storage backends on JVM.
 */
private class JvmEventImageStorage : EventImageStorage {
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
    remember { JvmEventImageStorage() }
