package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

// iOS/webview stub: simply echoes the local URI and reports instant completion.
private class IosBannerImageStorage : BannerImageStorage {
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
    remember { IosBannerImageStorage() }
