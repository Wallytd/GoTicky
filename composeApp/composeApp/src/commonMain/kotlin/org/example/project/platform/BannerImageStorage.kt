package org.example.project.platform

import androidx.compose.runtime.Composable

/**
 * Abstraction for uploading banner/hero images to backend storage (Firebase Storage on Android).
 * Return a publicly accessible URL on success, or null on failure.
 */
interface BannerImageStorage {
    suspend fun uploadBannerImage(
        bannerId: String,
        localUri: String,
        onProgress: (Float) -> Unit = {},
    ): String?
}

@Composable
expect fun rememberBannerImageStorage(): BannerImageStorage
