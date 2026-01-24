package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

// JS/Web stub: return the picked URI without remote upload.
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
actual fun rememberEventImageStorage(): EventImageStorage = remember { JsEventImageStorage() }
