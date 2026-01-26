package org.example.project.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.firestore

data class AdminProfile(
    val id: String,
    val fullName: String,
    val email: String,
    val passcode: String,
    val phoneNumber: String = "",
    val birthday: String = "",
    val gender: String = "",
    val country: String = "",
    val photoUri: String? = null,
    val rememberMe: Boolean = true,
) {
    val emailLower: String = email.trim().lowercase()
}

private fun DocumentSnapshot.stringField(key: String): String? = get(key) as? String
private fun DocumentSnapshot.boolField(key: String): Boolean? = get(key) as? Boolean

suspend fun fetchAdminProfiles(): List<AdminProfile> {
    println("DEBUG: Fetching admin profiles from Firestore...")
    try {
        val snapshot = Firebase.firestore.collection("adminProfiles").get()
        println("DEBUG: Retrieved ${snapshot.documents.size} documents from adminProfiles collection")
        
        val profiles = snapshot.documents.mapNotNull { doc ->
            println("DEBUG: Processing document with id: ${doc.id}")
            val email = doc.stringField("email")
            val passcode = doc.stringField("passcode") ?: doc.stringField("password")
            val fullName = doc.stringField("fullName") ?: doc.stringField("name")
            
            println("DEBUG: Document fields - email: $email, passcode: ${if (passcode != null) "[PRESENT]" else "[MISSING]"}, fullName: $fullName")
            
            if (email == null || passcode == null) {
                println("DEBUG: Skipping document ${doc.id} - missing required fields")
                return@mapNotNull null
            }
            
            val finalFullName = fullName ?: email.substringBefore("@")
            val id = doc.stringField("id") ?: doc.id

            AdminProfile(
                id = id,
                fullName = finalFullName,
                email = email,
                passcode = passcode,
                phoneNumber = doc.stringField("phoneNumber") ?: "",
                birthday = doc.stringField("birthday") ?: "",
                gender = doc.stringField("gender") ?: "",
                country = doc.stringField("country") ?: "",
                photoUri = doc.stringField("photoUri"),
                rememberMe = doc.boolField("rememberMe") ?: false,
            )
        }
        println("DEBUG: Successfully parsed ${profiles.size} admin profiles")
        return profiles
    } catch (e: Exception) {
        println("DEBUG ERROR: Exception while fetching admin profiles: ${e.message}")
        e.printStackTrace()
        throw e
    }
}

suspend fun adminProfileByCredentials(email: String, passcode: String): AdminProfile? {
    val normalizedEmail = email.trim().lowercase()
    val normalizedPass = passcode.trim()
    println("DEBUG: Looking for admin with email: $normalizedEmail")
    
    val profiles = fetchAdminProfiles()
    println("DEBUG: Total profiles fetched: ${profiles.size}")
    
    val match = profiles.firstOrNull { profile ->
        val emailMatch = profile.emailLower == normalizedEmail
        val passcodeMatch = profile.passcode == normalizedPass
        println("DEBUG: Checking profile ${profile.email} - emailMatch: $emailMatch, passcodeMatch: $passcodeMatch")
        emailMatch && passcodeMatch
    }
    
    if (match != null) {
        println("DEBUG: Found matching admin profile: ${match.email}")
    } else {
        println("DEBUG: No matching admin profile found")
    }
    
    return match
}

suspend fun rememberedAdminProfile(): AdminProfile? {
    val profiles = fetchAdminProfiles()
    return profiles.firstOrNull { it.rememberMe }
}


suspend fun updateAdminRememberMe(adminEmail: String, rememberMe: Boolean) {
    try {
        println("DEBUG: Updating rememberMe for $adminEmail to $rememberMe")
        val profiles = Firebase.firestore.collection("adminProfiles").get()
        
        // Find the document with matching email
        val matchingDoc = profiles.documents.firstOrNull { doc ->
            val email = doc.stringField("email")
            email?.trim()?.lowercase() == adminEmail.trim().lowercase()
        }
        
        if (matchingDoc != null) {
            println("DEBUG: Found admin profile document: ${matchingDoc.id}")
            Firebase.firestore.collection("adminProfiles")
                .document(matchingDoc.id)
                .update(mapOf("rememberMe" to rememberMe))
            println("DEBUG: Successfully updated rememberMe to $rememberMe")
        } else {
            println("DEBUG: No admin profile found with email: $adminEmail")
        }
    } catch (e: Exception) {
        println("DEBUG ERROR: Failed to update rememberMe: ${e.message}")
        e.printStackTrace()
    }
}
