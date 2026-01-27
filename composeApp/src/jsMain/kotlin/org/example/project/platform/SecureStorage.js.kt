package org.example.project.platform

import kotlinx.browser.localStorage
import org.w3c.dom.get
import org.w3c.dom.set

class JsSecureStorage : SecureStorage {
    
    private fun getEmailKey(email: String) = "goticky_email_${email.trim().lowercase()}"
    private fun getPasscodeKey(email: String) = "goticky_passcode_${email.trim().lowercase()}"
    private fun getRememberMeKey(email: String) = "goticky_remember_${email.trim().lowercase()}"
    
    override suspend fun saveAdminCredentials(email: String, passcode: String, rememberMe: Boolean) {
        val emailKey = getEmailKey(email)
        val passcodeKey = getPasscodeKey(email)
        val rememberKey = getRememberMeKey(email)
        
        if (rememberMe) {
            localStorage[emailKey] = email
            localStorage[passcodeKey] = passcode
            localStorage[rememberKey] = "true"
        } else {
            localStorage.removeItem(emailKey)
            localStorage.removeItem(passcodeKey)
            localStorage.removeItem(rememberKey)
        }
    }
    
    override suspend fun getAdminCredentials(email: String): AdminCredentials? {
        val rememberKey = getRememberMeKey(email)
        val rememberMe = localStorage[rememberKey] == "true"
        if (!rememberMe) return null
        
        val emailKey = getEmailKey(email)
        val passcodeKey = getPasscodeKey(email)
        
        val storedEmail = localStorage[emailKey]
        val passcode = localStorage[passcodeKey]
        
        if (storedEmail.isNullOrBlank() || passcode.isNullOrBlank()) {
            return null
        }
        
        return AdminCredentials(storedEmail, passcode, rememberMe)
    }
    
    override suspend fun clearAdminCredentials(email: String) {
        if (email.isBlank()) {
            // Clear all goticky admin credentials
            val keysToRemove = mutableListOf<String>()
            for (i in 0 until localStorage.length) {
                val key = localStorage.key(i)
                if (key?.startsWith("goticky_email_") == true ||
                    key?.startsWith("goticky_passcode_") == true ||
                    key?.startsWith("goticky_remember_") == true) {
                    keysToRemove.add(key)
                }
            }
            keysToRemove.forEach { localStorage.removeItem(it) }
        } else {
            val emailKey = getEmailKey(email)
            val passcodeKey = getPasscodeKey(email)
            val rememberKey = getRememberMeKey(email)
            
            localStorage.removeItem(emailKey)
            localStorage.removeItem(passcodeKey)
            localStorage.removeItem(rememberKey)
        }
    }
    
    override suspend fun hasCredentialsForEmail(email: String): Boolean {
        val rememberKey = getRememberMeKey(email)
        return localStorage[rememberKey] == "true"
    }
}

actual fun getSecureStorage(): SecureStorage {
    return JsSecureStorage()
}
