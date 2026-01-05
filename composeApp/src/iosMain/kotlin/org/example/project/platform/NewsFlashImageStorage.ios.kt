package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

// iOS/webview stub: echoes the local URI.
private class IosNewsFlashImageStorage : NewsFlashImageStorage {
    override suspend fun uploadNewsFlashImage(
        flashId: String,
        localUri: String,
        onProgress: (Float) -> Unit,
    ): NewsFlashImageUploadResult? {
        onProgress(1f)
        val suffix = localUri.hashCode().toUInt().toString()
        val path = "newsflash/$flashId/local-$suffix.jpg"
        return NewsFlashImageUploadResult(downloadUrl = localUri, storagePath = path)
    }
}

@Composable
actual fun rememberNewsFlashImageStorage(): NewsFlashImageStorage =
    remember { IosNewsFlashImageStorage() }
