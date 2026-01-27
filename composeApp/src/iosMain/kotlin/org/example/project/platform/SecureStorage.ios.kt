package org.example.project.platform

import platform.Foundation.NSUserDefaults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IosSecureStorage : SecureStorage {
    
    private val userDefaults = NSUserDefaults.standardUserDefaults
    
    private fun getEmailKey(email: String) = "email_${email.trim().lowercase()}"
    private fun getPasscodeKey(email: String) = "passcode_${email.trim().lowercase()}"
    private fun getRememberMeKey(email: String) = "remember_${email.trim().lowercase()}"
    
    override suspend fun saveAdminCredentials(email: String, passcode: String, rememberMe: Boolean) = withContext(Dispatchers.Main) {
        val emailKey = getEmailKey(email)
        val passcodeKey = getPasscodeKey(email)
        val rememberKey = getRememberMeKey(email)
        
        if (rememberMe) {
            userDefaults.setObject(email, emailKey)
            userDefaults.setObject(passcode, passcodeKey)
            userDefaults.setBool(true, rememberKey)
        } else {
            userDefaults.removeObjectForKey(emailKey)
            userDefaults.removeObjectForKey(passcodeKey)
            userDefaults.removeObjectForKey(rememberKey)
        }
        userDefaults.synchronize()
    }
    
    override suspend fun getAdminCredentials(email: String): AdminCredentials? = withContext(Dispatchers.Main) {
        val rememberKey = getRememberMeKey(email)
        val rememberMe = userDefaults.boolForKey(rememberKey)
        if (!rememberMe) return@withContext null
        
        val emailKey = getEmailKey(email)
        val passcodeKey = getPasscodeKey(email)
        
        val storedEmail = userDefaults.stringForKey(emailKey)
        val passcode = userDefaults.stringForKey(passcodeKey)
        
        if (storedEmail.isNullOrBlank() || passcode.isNullOrBlank()) {
            return@withContext null
        }
        
        AdminCredentials(storedEmail, passcode, rememberMe)
    }
    
    override suspend fun clearAdminCredentials(email: String) = withContext(Dispatchers.Main) {
        if (email.isBlank()) {
            // Clear all - would need to iterate through all keys in production
            // For now, just removing common keys
            userDefaults.dictionaryRepresentation().keys.forEach { key ->
                if ((key as? String)?.startsWith("email_") == true ||
                    (key as? String)?.startsWith("passcode_") == true ||
                    (key as? String)?.startsWith("remember_") == true) {
                    userDefaults.removeObjectForKey(key as String)
                }
            }
        } else {
            val emailKey = getEmailKey(email)
            val passcodeKey = getPasscodeKey(email)
            val rememberKey = getRememberMeKey(email)
            
            userDefaults.removeObjectForKey(emailKey)
            userDefaults.removeObjectForKey(passcodeKey)
            userDefaults.removeObjectForKey(rememberKey)
        }
        userDefaults.synchronize()
    }
    
    override suspend fun hasCredentialsForEmail(email: String): Boolean = withContext(Dispatchers.Main) {
        val rememberKey = getRememberMeKey(email)
        userDefaults.boolForKey(rememberKey)
    }
}

actual fun getSecureStorage(): SecureStorage {
    return IosSecureStorage()
}
