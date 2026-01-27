package org.example.project.platform

import platform.Foundation.NSUserDefaults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val KEY_ADMIN_EMAIL = "admin_email"
private const val KEY_ADMIN_PASSCODE = "admin_passcode"
private const val KEY_REMEMBER_ME = "remember_me"

class IosSecureStorage : SecureStorage {
    
    private val userDefaults = NSUserDefaults.standardUserDefaults
    
    override suspend fun saveAdminCredentials(email: String, passcode: String, rememberMe: Boolean) = withContext(Dispatchers.Main) {
        if (rememberMe) {
            userDefaults.setObject(email, KEY_ADMIN_EMAIL)
            userDefaults.setObject(passcode, KEY_ADMIN_PASSCODE)
            userDefaults.setBool(true, KEY_REMEMBER_ME)
        } else {
            userDefaults.removeObjectForKey(KEY_ADMIN_EMAIL)
            userDefaults.removeObjectForKey(KEY_ADMIN_PASSCODE)
            userDefaults.setBool(false, KEY_REMEMBER_ME)
        }
        userDefaults.synchronize()
    }
    
    override suspend fun getAdminCredentials(): AdminCredentials? = withContext(Dispatchers.Main) {
        val rememberMe = userDefaults.boolForKey(KEY_REMEMBER_ME)
        if (!rememberMe) return@withContext null
        
        val email = userDefaults.stringForKey(KEY_ADMIN_EMAIL)
        val passcode = userDefaults.stringForKey(KEY_ADMIN_PASSCODE)
        
        if (email.isNullOrBlank() || passcode.isNullOrBlank()) {
            return@withContext null
        }
        
        AdminCredentials(email, passcode, rememberMe)
    }
    
    override suspend fun clearAdminCredentials() = withContext(Dispatchers.Main) {
        userDefaults.removeObjectForKey(KEY_ADMIN_EMAIL)
        userDefaults.removeObjectForKey(KEY_ADMIN_PASSCODE)
        userDefaults.removeObjectForKey(KEY_REMEMBER_ME)
        userDefaults.synchronize()
    }
}

actual fun getSecureStorage(): SecureStorage {
    return IosSecureStorage()
}
