package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

// JS/web stub: cannot upload; returns the provided local URI.
private class JsBannerImageStorage : BannerImageStorage {
    override suspend fun uploadBannerImage(
        bannerId: String,
        localUri: String,
        onProgress: (Float) -> Unit,
    ): String? {
        onProgress(1f)
        return localUri
    }
}

@Composable
actual fun rememberBannerImageStorage(): BannerImageStorage =
    remember { JsBannerImageStorage() }
