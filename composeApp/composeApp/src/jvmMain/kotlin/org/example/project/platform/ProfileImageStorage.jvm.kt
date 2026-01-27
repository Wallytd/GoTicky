package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

// Desktop stub: no real upload, just reports success with the original URI.
private class JvmProfileImageStorage : ProfileImageStorage {
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
    remember { JvmProfileImageStorage() }
