package org.example.project.platform

import java.util.prefs.Preferences

class JvmSecureStorage : SecureStorage {
    
    private val prefs = Preferences.userNodeForPackage(JvmSecureStorage::class.java)
    
    private fun getEmailKey(email: String) = "email_${email.trim().lowercase()}"
    private fun getPasscodeKey(email: String) = "passcode_${email.trim().lowercase()}"
    private fun getRememberMeKey(email: String) = "remember_${email.trim().lowercase()}"
    
    override suspend fun saveAdminCredentials(email: String, passcode: String, rememberMe: Boolean) {
        val emailKey = getEmailKey(email)
        val passcodeKey = getPasscodeKey(email)
        val rememberKey = getRememberMeKey(email)
        
        if (rememberMe) {
            prefs.put(emailKey, email)
            prefs.put(passcodeKey, passcode)
            prefs.putBoolean(rememberKey, true)
        } else {
            prefs.remove(emailKey)
            prefs.remove(passcodeKey)
            prefs.remove(rememberKey)
        }
        prefs.flush()
    }
    
    override suspend fun getAdminCredentials(email: String): AdminCredentials? {
        val rememberKey = getRememberMeKey(email)
        val rememberMe = prefs.getBoolean(rememberKey, false)
        if (!rememberMe) return null
        
        val emailKey = getEmailKey(email)
        val passcodeKey = getPasscodeKey(email)
        
        val storedEmail = prefs.get(emailKey, null)
        val passcode = prefs.get(passcodeKey, null)
        
        if (storedEmail.isNullOrBlank() || passcode.isNullOrBlank()) {
            return null
        }
        
        return AdminCredentials(storedEmail, passcode, rememberMe)
    }
    
    override suspend fun clearAdminCredentials(email: String) {
        if (email.isBlank()) {
            // Clear all admin credentials
            prefs.keys().filter { 
                it.startsWith("email_") || it.startsWith("passcode_") || it.startsWith("remember_")
            }.forEach { prefs.remove(it) }
        } else {
            val emailKey = getEmailKey(email)
            val passcodeKey = getPasscodeKey(email)
            val rememberKey = getRememberMeKey(email)
            
            prefs.remove(emailKey)
            prefs.remove(passcodeKey)
            prefs.remove(rememberKey)
        }
        prefs.flush()
    }
    
    override suspend fun hasCredentialsForEmail(email: String): Boolean {
        val rememberKey = getRememberMeKey(email)
        return prefs.getBoolean(rememberKey, false)
    }
}

actual fun getSecureStorage(): SecureStorage {
    return JvmSecureStorage()
}
