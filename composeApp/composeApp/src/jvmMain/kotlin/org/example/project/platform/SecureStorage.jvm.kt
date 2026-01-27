package org.example.project.platform

import java.util.prefs.Preferences

private const val KEY_ADMIN_EMAIL = "admin_email"
private const val KEY_ADMIN_PASSCODE = "admin_passcode"
private const val KEY_REMEMBER_ME = "remember_me"

class JvmSecureStorage : SecureStorage {
    
    private val prefs = Preferences.userNodeForPackage(JvmSecureStorage::class.java)
    
    override suspend fun saveAdminCredentials(email: String, passcode: String, rememberMe: Boolean) {
        if (rememberMe) {
            prefs.put(KEY_ADMIN_EMAIL, email)
            prefs.put(KEY_ADMIN_PASSCODE, passcode)
            prefs.putBoolean(KEY_REMEMBER_ME, true)
        } else {
            prefs.remove(KEY_ADMIN_EMAIL)
            prefs.remove(KEY_ADMIN_PASSCODE)
            prefs.putBoolean(KEY_REMEMBER_ME, false)
        }
        prefs.flush()
    }
    
    override suspend fun getAdminCredentials(): AdminCredentials? {
        val rememberMe = prefs.getBoolean(KEY_REMEMBER_ME, false)
        if (!rememberMe) return null
        
        val email = prefs.get(KEY_ADMIN_EMAIL, null)
        val passcode = prefs.get(KEY_ADMIN_PASSCODE, null)
        
        if (email.isNullOrBlank() || passcode.isNullOrBlank()) {
            return null
        }
        
        return AdminCredentials(email, passcode, rememberMe)
    }
    
    override suspend fun clearAdminCredentials() {
        prefs.remove(KEY_ADMIN_EMAIL)
        prefs.remove(KEY_ADMIN_PASSCODE)
        prefs.remove(KEY_REMEMBER_ME)
        prefs.flush()
    }
}

actual fun getSecureStorage(): SecureStorage {
    return JvmSecureStorage()
}
