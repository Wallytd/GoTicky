package org.example.project.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore

data class AdminSeed(
    val id: String,
    val fullName: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val birthday: String,
    val gender: String,
    val country: String,
    val photoUri: String,
)

// Keep this list in sync with goticky.json adminProfiles.
val adminSeeds: List<AdminSeed> = listOf(
    AdminSeed(
        id = "1",
        fullName = "Kate Mula",
        email = "kate@goticky.com",
        password = "kate2026",
        phoneNumber = "+263771234567",
        birthday = "1989-04-11",
        gender = "Female",
        country = "Zimbabwe",
        photoUri = "https://firebasestorage.googleapis.com/v0/b/goticky-47533.firebasestorage.app/o/Kate%20Mula.jpeg?alt=media&token=53a92d5d-a158-483e-9a2d-0ee6fb84b23c"
    ),
    AdminSeed(
        id = "2",
        fullName = "Rob Johnson",
        email = "rob@goticky.com",
        password = "rob2026",
        phoneNumber = "+263781234568",
        birthday = "1991-09-23",
        gender = "Male",
        country = "Zimbabwe",
        photoUri = "https://firebasestorage.googleapis.com/v0/b/goticky-47533.firebasestorage.app/o/Rob%20Johnson.jpeg?alt=media&token=5d16a2c8-526a-431b-81da-cfb8812caba2"
    ),
    AdminSeed(
        id = "3",
        fullName = "Walter Dru",
        email = "walter@goticky.com",
        password = "walter2026",
        phoneNumber = "+263731234569",
        birthday = "1993-02-07",
        gender = "Non-binary",
        country = "Zimbabwe",
        photoUri = "https://firebasestorage.googleapis.com/v0/b/goticky-47533.firebasestorage.app/o/Walter%20Dru.jpeg?alt=media&token=0b9d9abd-166c-4226-bfde-1545530aa16a"
    ),
)

fun adminSeedByCredentials(email: String, password: String): AdminSeed? {
    val e = email.trim().lowercase()
    return adminSeeds.firstOrNull { it.email.trim().lowercase() == e && it.password == password }
}

suspend fun seedAdminProfilesIfMissing() {
    val firestore = Firebase.firestore
    val auth = Firebase.auth
    // Ensure we have an auth session to satisfy Firestore rules.
    val hadSession = auth.currentUser != null
    val signedInAnon = if (!hadSession) {
        runCatching { auth.signInAnonymously() }.isSuccess
    } else false

    adminSeeds.forEach { admin ->
        // Use full name as document id for the adminProfiles collection.
        val adminDoc = firestore.collection("adminProfiles").document(admin.fullName)
        val existing = runCatching { adminDoc.get() }.getOrNull()
        if (existing == null || !existing.exists) {
            adminDoc.set(
                mapOf(
                    "id" to admin.id,
                    "fullName" to admin.fullName,
                    "email" to admin.email,
                    "password" to admin.password,
                    "phoneNumber" to admin.phoneNumber,
                    "birthday" to admin.birthday,
                    "gender" to admin.gender,
                    "country" to admin.country,
                    "photoUri" to admin.photoUri,
                    "role" to "admin",
                )
            )
        }

        // Mirror into users collection with admin role keyed by lowercased email.
        val userId = admin.email.lowercase()
        val userDoc = firestore.collection("users").document(userId)
        val userExisting = runCatching { userDoc.get() }.getOrNull()
        if (userExisting == null || !userExisting.exists) {
            userDoc.set(
                mapOf(
                    "uid" to userId,
                    "displayName" to admin.fullName,
                    "email" to admin.email,
                    "role" to "admin",
                    "phoneNumber" to admin.phoneNumber,
                    "birthday" to admin.birthday,
                    "gender" to admin.gender,
                    "countryName" to admin.country,
                    "photoUri" to admin.photoUri,
                )
            )
        } else {
            // Ensure role stays admin if the document already exists.
            userDoc.update(mapOf("role" to "admin"))
        }

        // Lightweight public index for avatar lookup keyed by emailLower.
        firestore.collection("publicProfiles").document(admin.email.lowercase()).set(
            mapOf(
                "emailLower" to admin.email.lowercase(),
                "email" to admin.email,
                "displayName" to admin.fullName,
                "photoUri" to admin.photoUri,
            ),
            merge = true
        )
    }

    if (signedInAnon && !hadSession) {
        runCatching { auth.signOut() }
    }
}
