package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

// wasm/js stub: echo the URI; no-op upload.
private class WasmBannerImageStorage : BannerImageStorage {
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
    remember { WasmBannerImageStorage() }
