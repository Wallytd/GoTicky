package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

// JS/Web stub: simply return the chosen URI; no remote upload.
private class JsNewsFlashImageStorage : NewsFlashImageStorage {
    override suspend fun uploadNewsFlashImage(
        flashId: String,
        localUri: String,
        onProgress: (Float) -> Unit,
    ): NewsFlashImageUploadResult? {
        onProgress(1f)
        return NewsFlashImageUploadResult(downloadUrl = localUri, storagePath = localUri)
    }
}

@Composable
actual fun rememberNewsFlashImageStorage(): NewsFlashImageStorage = remember { JsNewsFlashImageStorage() }
