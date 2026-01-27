package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

// iOS stub: simply echoes the local URI and reports instant completion.
private class IosProfileImageStorage : ProfileImageStorage {
    override suspend fun uploadProfileImage(
        localUri: String,
        onProgress: (Float) -> Unit
    ): String? {
        onProgress(1f)
        return localUri
    }
}

@Composable
actual fun rememberProfileImageStorage(): ProfileImageStorage =
    remember { IosProfileImageStorage() }
