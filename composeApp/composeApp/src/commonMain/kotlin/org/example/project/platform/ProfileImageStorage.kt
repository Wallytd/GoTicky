package org.example.project.platform

import androidx.compose.runtime.Composable

/**
 * Abstraction for uploading profile images to the active backend (Firebase Storage on Android).
 *
 * Implementations should:
 * - Use the provided [localUri] as the source image.
 * - Call [onProgress] with values in [0f, 1f] as bytes are uploaded.
 * - Return a publicly resolvable URL (e.g., HTTPS download URL) on success, or null on failure.
 */
interface ProfileImageStorage {
    suspend fun uploadProfileImage(
        localUri: String,
        onProgress: (Float) -> Unit
    ): String?
}

@Composable
expect fun rememberProfileImageStorage(): ProfileImageStorage
