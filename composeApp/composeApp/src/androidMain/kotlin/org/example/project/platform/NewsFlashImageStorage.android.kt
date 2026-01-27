package org.example.project.platform

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

private class AndroidNewsFlashImageStorage(
    private val storage: FirebaseStorage = FirebaseStorage.getInstance(),
) : NewsFlashImageStorage {

    override suspend fun uploadNewsFlashImage(
        flashId: String,
        localUri: String,
        onProgress: (Float) -> Unit,
    ): NewsFlashImageUploadResult? {
        val fileUri = Uri.parse(localUri)
        val safeId = if (flashId.isBlank()) System.currentTimeMillis().toString() else flashId
        val path = "newsflash/$safeId/cover-${System.currentTimeMillis()}.jpg"
        val ref = storage.reference.child(path)

        return runCatching {
            putFileAwait(ref, fileUri, onProgress)
            val downloadUrl = downloadUrlAwait(ref)
            NewsFlashImageUploadResult(downloadUrl = downloadUrl, storagePath = path)
        }.getOrNull()
    }

    private suspend fun putFileAwait(
        ref: com.google.firebase.storage.StorageReference,
        fileUri: Uri,
        onProgress: (Float) -> Unit,
    ): UploadTask.TaskSnapshot = suspendCancellableCoroutine { cont ->
        val task = ref.putFile(fileUri)
        task.addOnProgressListener { snap ->
            val total = snap.totalByteCount
            val transferred = snap.bytesTransferred
            if (total > 0L) {
                onProgress((transferred.toFloat() / total.toFloat()).coerceIn(0f, 1f))
            }
        }
        task.addOnSuccessListener { snapshot ->
            if (cont.isActive) cont.resume(snapshot)
        }
        task.addOnFailureListener { e ->
            if (cont.isActive) cont.resumeWithException(e)
        }
        cont.invokeOnCancellation { task.cancel() }
    }

    private suspend fun downloadUrlAwait(
        ref: com.google.firebase.storage.StorageReference,
    ): String = suspendCancellableCoroutine { cont ->
        val task = ref.downloadUrl
        task.addOnSuccessListener { uri ->
            if (cont.isActive) cont.resume(uri.toString())
        }
        task.addOnFailureListener { e ->
            if (cont.isActive) cont.resumeWithException(e)
        }
    }
}

@Composable
actual fun rememberNewsFlashImageStorage(): NewsFlashImageStorage =
    remember { AndroidNewsFlashImageStorage() }
