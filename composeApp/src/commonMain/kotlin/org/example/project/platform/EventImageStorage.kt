package org.example.project.platform

import androidx.compose.runtime.Composable

interface EventImageStorage {
    /**
     * Uploads an event flyer/asset to backend storage and returns a publicly reachable URL on success.
     */
    suspend fun uploadEventFlyer(
        organizerId: String,
        eventId: String,
        localUri: String,
        onProgress: (Float) -> Unit = {},
    ): String?
}

@Composable
expect fun rememberEventImageStorage(): EventImageStorage
