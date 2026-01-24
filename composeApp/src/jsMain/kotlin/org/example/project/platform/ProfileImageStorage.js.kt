package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

// JS/Web stub: echo back the local URI instead of remote upload.
private class JsProfileImageStorage : ProfileImageStorage {
    override suspend fun uploadProfileImage(
        localUri: String,
        onProgress: (Float) -> Unit,
    ): String? {
        onProgress(1f)
        return localUri
    }
}

@Composable
actual fun rememberProfileImageStorage(): ProfileImageStorage = remember { JsProfileImageStorage() }
