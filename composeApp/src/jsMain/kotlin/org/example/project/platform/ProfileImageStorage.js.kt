package org.example.project.platform

import androidx.compose.runtime.Composable

private class JsProfileImageStorage : ProfileImageStorage {
    override suspend fun uploadProfileImage(
        localUri: String,
        onProgress: (Float) -> Unit
    ): String? {
        // JS/web stub: report immediate completion
        onProgress(1f)
        return null
    }
}

@Composable
actual fun rememberProfileImageStorage(): ProfileImageStorage = JsProfileImageStorage()
