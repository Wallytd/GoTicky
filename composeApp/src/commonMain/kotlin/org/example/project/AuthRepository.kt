package org.example.project

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import kotlinx.datetime.Instant

sealed class AuthResult {
    object Success : AuthResult()
    data class Error(val message: String) : AuthResult()
}

data class AuthUser(
    val uid: String,
    val email: String?,
    val displayName: String?,
)

interface AuthRepository {
    suspend fun signIn(email: String, password: String): AuthResult
    suspend fun signUp(profile: UserProfile, password: String): AuthResult
    suspend fun signOut()
    fun currentUser(): AuthUser?
    suspend fun fetchProfile(): UserProfile?
    suspend fun updateProfile(profile: UserProfile): AuthResult
    suspend fun findProfileByEmail(email: String): UserProfile?
    suspend fun updateFavorites(favorites: List<String>): AuthResult
    suspend fun fetchFavorites(): List<String>
}

class FirebaseAuthRepository(
    private val auth: FirebaseAuth = Firebase.auth
) : AuthRepository {

    override suspend fun signIn(email: String, password: String): AuthResult {
        return try {
            auth.signInWithEmailAndPassword(email, password)
            AuthResult.Success
        } catch (t: Throwable) {
            AuthResult.Error(readableMessage(t))
        }
    }

    override suspend fun findProfileByEmail(email: String): UserProfile? {
        val key = email.trim().lowercase()
        if (key.isBlank()) return null
        return try {
            // Public, minimal index to allow pre-auth lookup on the sign-in screen.
            val doc = Firebase.firestore
                .collection("publicProfiles")
                .document(key)
                .get()

            if (!doc.exists) return null
            UserProfile(
                fullName = doc.get<String?>("displayName") ?: "",
                email = doc.get<String?>("email") ?: email.trim(),
                countryName = "Zimbabwe",
                countryFlag = "",
                phoneCode = "+263",
                phoneNumber = "",
                birthday = "",
                gender = "",
                photoResKey = null,
                photoUri = doc.get<String?>("photoUri"),
            )
        } catch (_: Throwable) {
            null
        }
    }

    override suspend fun signUp(profile: UserProfile, password: String): AuthResult {
        return try {
            auth.createUserWithEmailAndPassword(profile.email, password)
            auth.currentUser?.updateProfile(displayName = profile.fullName, photoUrl = null)
            // Create a minimal user doc in Firestore
            val uid = auth.currentUser?.uid
            if (uid != null) {
                // Canonical per-user document keyed by Firebase UID
                Firebase.firestore.collection("users").document(uid).set(
                    mapOf(
                        "uid" to uid,
                        "displayName" to profile.fullName,
                        "email" to profile.email,
                        "createdAt" to currentTimestampIsoString(),
                        "role" to "customer",
                        "countryName" to profile.countryName,
                        "countryFlag" to profile.countryFlag,
                        "phoneCode" to profile.phoneCode,
                        "phoneNumber" to profile.phoneNumber,
                        "birthday" to profile.birthday,
                        "gender" to profile.gender,
                        "photoResKey" to profile.photoResKey,
                        "photoUri" to profile.photoUri,
                        "favorites" to profile.favorites
                    )
                )

                // Human-friendly index under /userDirectory/{role}/{nameKey}/{uid}
                userDirectoryDocument(profile, uid).set(
                    mapOf(
                        "uid" to uid,
                        "displayName" to profile.fullName,
                        "email" to profile.email,
                        "createdAt" to currentTimestampIsoString(),
                        "role" to "customer",
                        "countryName" to profile.countryName,
                        "countryFlag" to profile.countryFlag,
                        "phoneCode" to profile.phoneCode,
                        "phoneNumber" to profile.phoneNumber,
                        "birthday" to profile.birthday,
                        "gender" to profile.gender,
                        "photoResKey" to profile.photoResKey,
                        "photoUri" to profile.photoUri
                    )
                )

                // Public, minimal index for pre-auth avatar lookup (keyed by lowercase email).
                val emailKey = profile.email.trim().lowercase()
                Firebase.firestore.collection("publicProfiles").document(emailKey).set(
                    mapOf(
                        "emailLower" to emailKey,
                        "email" to profile.email.trim(),
                        "displayName" to profile.fullName,
                        "photoUri" to profile.photoUri
                    )
                )
            }
            AuthResult.Success
        } catch (t: Throwable) {
            AuthResult.Error(readableMessage(t))
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override fun currentUser(): AuthUser? {
        val user = auth.currentUser ?: return null
        return AuthUser(
            uid = user.uid,
            email = user.email,
            displayName = user.displayName,
        )
    }

    override suspend fun fetchProfile(): UserProfile? {
        val uid = auth.currentUser?.uid ?: return null
        return try {
            val doc = Firebase.firestore.collection("users").document(uid).get()
            val profile = UserProfile(
                fullName = doc.get<String?>("displayName") ?: "",
                email = doc.get<String?>("email") ?: "",
                countryName = doc.get<String?>("countryName") ?: "Zimbabwe",
                countryFlag = doc.get<String?>("countryFlag") ?: "",
                phoneCode = doc.get<String?>("phoneCode") ?: "+263",
                phoneNumber = doc.get<String?>("phoneNumber") ?: "",
                birthday = doc.get<String?>("birthday") ?: "",
                gender = doc.get<String?>("gender") ?: "",
                photoResKey = doc.get<String?>("photoResKey"),
                photoUri = doc.get<String?>("photoUri"),
                favorites = doc.get<List<String>?>("favorites") ?: emptyList(),
                role = doc.get<String?>("role") ?: "customer",
            )

            // Ensure the public, minimal index is created/updated for pre-auth avatar lookup.
            try {
                val emailKey = profile.email.trim().lowercase()
                if (emailKey.isNotBlank()) {
                    Firebase.firestore.collection("publicProfiles").document(emailKey).set(
                        mapOf(
                            "emailLower" to emailKey,
                            "email" to profile.email.trim(),
                            "displayName" to profile.fullName,
                            "photoUri" to profile.photoUri,
                        ),
                        merge = true
                    )
                }
            } catch (_: Throwable) {
                // Best-effort only; don't break sign-in if this fails.
            }

            profile
        } catch (_: Throwable) {
            null
        }
    }

    override suspend fun updateProfile(profile: UserProfile): AuthResult {
        val uid = auth.currentUser?.uid ?: return AuthResult.Error("Not signed in")
        return try {
            // Update auth display name
            auth.currentUser?.updateProfile(displayName = profile.fullName, photoUrl = null)
            // Upsert canonical Firestore profile
            Firebase.firestore.collection("users").document(uid).set(
                mapOf(
                    "uid" to uid,
                    "displayName" to profile.fullName,
                    "email" to profile.email,
                    "countryName" to profile.countryName,
                    "countryFlag" to profile.countryFlag,
                    "phoneCode" to profile.phoneCode,
                    "phoneNumber" to profile.phoneNumber,
                    "birthday" to profile.birthday,
                    "gender" to profile.gender,
                    "photoResKey" to profile.photoResKey,
                    "photoUri" to profile.photoUri,
                    "favorites" to profile.favorites,
                ),
                merge = true
            )

            // Keep the public index in sync for pre-auth avatar lookup.
            val emailKey = profile.email.trim().lowercase()
            Firebase.firestore.collection("publicProfiles").document(emailKey).set(
                mapOf(
                    "emailLower" to emailKey,
                    "email" to profile.email.trim(),
                    "displayName" to profile.fullName,
                    "photoUri" to profile.photoUri,
                ),
                merge = true
            )

            // Keep userDirectory index in sync (create or update entry)
            userDirectoryDocument(profile, uid).set(
                mapOf(
                    "uid" to uid,
                    "displayName" to profile.fullName,
                    "email" to profile.email,
                    "role" to "customer",
                    "countryName" to profile.countryName,
                    "countryFlag" to profile.countryFlag,
                    "phoneCode" to profile.phoneCode,
                    "phoneNumber" to profile.phoneNumber,
                    "birthday" to profile.birthday,
                    "gender" to profile.gender,
                    "photoResKey" to profile.photoResKey,
                    "photoUri" to profile.photoUri,
                ),
                merge = true
            )
            AuthResult.Success
        } catch (t: Throwable) {
            AuthResult.Error(readableMessage(t))
        }
    }

    override suspend fun updateFavorites(favorites: List<String>): AuthResult {
        val uid = auth.currentUser?.uid ?: return AuthResult.Error("Not signed in")
        return try {
            Firebase.firestore.collection("users").document(uid).set(
                mapOf(
                    "favorites" to favorites.distinct().take(500),
                ),
                merge = true
            )
            AuthResult.Success
        } catch (t: Throwable) {
            AuthResult.Error(readableMessage(t))
        }
    }

    override suspend fun fetchFavorites(): List<String> {
        val uid = auth.currentUser?.uid ?: return emptyList()
        return try {
            val doc = Firebase.firestore.collection("users").document(uid).get()
            doc.get<List<String>?>("favorites") ?: emptyList()
        } catch (_: Throwable) {
            emptyList()
        }
    }

    private fun readableMessage(t: Throwable): String {
        val raw = t.message ?: return "Something went wrong"
        return when {
            raw.contains("already in use", ignoreCase = true) ||
                raw.contains("collision", ignoreCase = true) ->
                "That email is already registered. Try signing in or use a different address."
            raw.contains("password", ignoreCase = true) -> "Check your password and try again."
            raw.contains("email", ignoreCase = true) -> "That email looks invalid or is already in use."
            else -> raw
        }
    }

    @OptIn(kotlin.time.ExperimentalTime::class)
    private fun currentTimestampIsoString(): String =
        currentInstant().toString()
    
    private fun userDirectoryDocument(profile: UserProfile, uid: String) =
        Firebase.firestore
            .collection("userDirectory")
            .document("customer")
            .collection(sanitizedNameKey(profile.fullName))
            .document(uid)
}

private fun sanitizedNameKey(name: String): String =
    name.trim().lowercase()
        .replace(Regex("[^a-z0-9]+"), "-")
        .trim('-')
        .ifBlank { "anonymous" }
