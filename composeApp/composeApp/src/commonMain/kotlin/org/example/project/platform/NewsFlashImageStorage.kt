package org.example.project.platform

import androidx.compose.runtime.Composable

data class NewsFlashImageUploadResult(
    val downloadUrl: String,
    val storagePath: String,
)

/**
 * Uploads News Flash cover images to backend storage (Firebase Storage on Android).
 * Returns a publicly reachable URL + storage path on success, or null on failure.
 */
interface NewsFlashImageStorage {
    suspend fun uploadNewsFlashImage(
        flashId: String,
        localUri: String,
        onProgress: (Float) -> Unit = {},
    ): NewsFlashImageUploadResult?
}

@Composable
expect fun rememberNewsFlashImageStorage(): NewsFlashImageStorage
