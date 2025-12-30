package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

// Web/JS stub: echo local URI.
private class JsNewsFlashImageStorage : NewsFlashImageStorage {
    override suspend fun uploadNewsFlashImage(
        flashId: String,
        localUri: String,
        onProgress: (Float) -> Unit,
    ): NewsFlashImageUploadResult? {
        onProgress(1f)
        val path = "newsflash/$flashId/local-${js("Date.now()")}.jpg"
        return NewsFlashImageUploadResult(downloadUrl = localUri, storagePath = path)
    }
}

@Composable
actual fun rememberNewsFlashImageStorage(): NewsFlashImageStorage =
    remember { JsNewsFlashImageStorage() }
