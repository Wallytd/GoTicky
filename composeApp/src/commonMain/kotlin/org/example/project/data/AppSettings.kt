@file:OptIn(kotlin.time.ExperimentalTime::class)

package org.example.project.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import org.example.project.currentInstant

/**
 * Manages user-specific app settings stored in Firestore.
 * This allows preferences to sync across a user's devices.
 */

data class UserAppSettings(
    val adminRememberMe: Boolean = false,
    val lastLoginEmail: String = "", // Store only email, never password
)

/**
 * Get app settings for a specific admin user from Firestore.
 * Settings are stored in: users/{emailLower}/app_settings/preferences
 * Note: Admin user documents use email (lowercased) as the document ID, not the numeric profile ID
 */
suspend fun getAdminAppSettings(adminEmail: String): UserAppSettings? {
    return try {
        val emailLower = adminEmail.trim().lowercase()
        
        val settingsDoc = Firebase.firestore
            .collection("users")
            .document(emailLower)
            .collection("app_settings")
            .document("preferences")
            .get()
        
        if (settingsDoc.exists) {
            UserAppSettings(
                adminRememberMe = settingsDoc.get<Boolean?>("adminRememberMe") ?: false,
                lastLoginEmail = settingsDoc.get<String?>("lastLoginEmail") ?: "",
            )
        } else {
            null
        }
    } catch (e: Exception) {
        println("ERROR: Failed to get admin app settings: ${e.message}")
        e.printStackTrace()
        null
    }
}

/**
 * Update app settings for a specific admin user in Firestore.
 * Note: Only stores preferences, NEVER stores passwords/passcodes.
 * Admin user documents use email (lowercased) as the document ID, not the numeric profile ID.
 */
@OptIn(kotlin.time.ExperimentalTime::class)
suspend fun updateAdminAppSettings(adminEmail: String, rememberMe: Boolean) {
    try {
        val emailLower = adminEmail.trim().lowercase()
        
        println("DEBUG: Updating app settings for admin at users/$emailLower/app_settings/preferences")
        
        val settingsRef = Firebase.firestore
            .collection("users")
            .document(emailLower)
            .collection("app_settings")
            .document("preferences")
        
        val settings = mapOf(
            "adminRememberMe" to rememberMe,
            "lastLoginEmail" to if (rememberMe) adminEmail else "",
            "updatedAt" to currentInstant().toEpochMilliseconds()
        )
        
        settingsRef.set(settings)
        println("DEBUG: Successfully updated admin app settings at users/$emailLower/app_settings/preferences (rememberMe=$rememberMe)")
    } catch (e: Exception) {
        println("ERROR: Failed to update admin app settings: ${e.message}")
        e.printStackTrace()
    }
}

/**
 * Check if any admin has rememberMe enabled in their app settings.
 * Returns the email of the admin who should be remembered, if any.
 * Admin user documents use email (lowercased) as the document ID, not the numeric profile ID.
 */
suspend fun getRememberedAdminEmail(): String? {
    return try {
        val adminProfiles = fetchAdminProfiles()
        
        for (profile in adminProfiles) {
            val emailLower = profile.email.trim().lowercase()
            val settingsDoc = Firebase.firestore
                .collection("users")
                .document(emailLower)
                .collection("app_settings")
                .document("preferences")
                .get()
            
            if (settingsDoc.exists) {
                val rememberMe = settingsDoc.get<Boolean?>("adminRememberMe") ?: false
                if (rememberMe) {
                    return profile.email
                }
            }
        }
        null
    } catch (e: Exception) {
        println("ERROR: Failed to get remembered admin email: ${e.message}")
        null
    }
}
