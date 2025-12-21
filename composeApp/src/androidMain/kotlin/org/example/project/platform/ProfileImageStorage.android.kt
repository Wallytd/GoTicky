package org.example.project.platform

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

private class AndroidProfileImageStorage(
    private val storage: FirebaseStorage = FirebaseStorage.getInstance(),
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
) : ProfileImageStorage {

    override suspend fun uploadProfileImage(
        localUri: String,
        onProgress: (Float) -> Unit
    ): String? {
        val user = auth.currentUser ?: return null
        val fileUri = Uri.parse(localUri)
        val path = "users/${user.uid}/profile/${System.currentTimeMillis()}.jpg"
        val ref = storage.reference.child(path)

        // Upload the file, bridging the Task API into coroutines and forwarding progress.
        try {
            putFileAwait(ref, fileUri, onProgress)
        } catch (_: Throwable) {
            return null
        }

        // Fetch the download URL once upload completes.
        return try {
            downloadUrlAwait(ref)
        } catch (_: Throwable) {
            null
        }
    }

    private suspend fun putFileAwait(
        ref: com.google.firebase.storage.StorageReference,
        fileUri: Uri,
        onProgress: (Float) -> Unit
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
        ref: com.google.firebase.storage.StorageReference
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
actual fun rememberProfileImageStorage(): ProfileImageStorage =
    remember { AndroidProfileImageStorage() }
