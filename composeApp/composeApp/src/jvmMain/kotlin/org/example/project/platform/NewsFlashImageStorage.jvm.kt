package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

// Desktop/JVM stub: echo local URI.
private class JvmNewsFlashImageStorage : NewsFlashImageStorage {
    override suspend fun uploadNewsFlashImage(
        flashId: String,
        localUri: String,
        onProgress: (Float) -> Unit,
    ): NewsFlashImageUploadResult? {
        onProgress(1f)
        val path = "newsflash/$flashId/local-${System.currentTimeMillis()}.jpg"
        return NewsFlashImageUploadResult(downloadUrl = localUri, storagePath = path)
    }
}

@Composable
actual fun rememberNewsFlashImageStorage(): NewsFlashImageStorage =
    remember { JvmNewsFlashImageStorage() }
