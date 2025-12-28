package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

// Desktop/JVM stub: simply echoes the local URI; no-op upload.
private class JvmBannerImageStorage : BannerImageStorage {
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
    remember { JvmBannerImageStorage() }
