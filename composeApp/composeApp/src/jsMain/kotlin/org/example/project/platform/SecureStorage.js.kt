package org.example.project.platform

import kotlinx.browser.localStorage
import org.w3c.dom.get
import org.w3c.dom.set

private const val KEY_ADMIN_EMAIL = "goticky_admin_email"
private const val KEY_ADMIN_PASSCODE = "goticky_admin_passcode"
private const val KEY_REMEMBER_ME = "goticky_admin_remember_me"

class JsSecureStorage : SecureStorage {
    
    override suspend fun saveAdminCredentials(email: String, passcode: String, rememberMe: Boolean) {
        if (rememberMe) {
            localStorage[KEY_ADMIN_EMAIL] = email
            localStorage[KEY_ADMIN_PASSCODE] = passcode
            localStorage[KEY_REMEMBER_ME] = "true"
        } else {
            localStorage.removeItem(KEY_ADMIN_EMAIL)
            localStorage.removeItem(KEY_ADMIN_PASSCODE)
            localStorage[KEY_REMEMBER_ME] = "false"
        }
    }
    
    override suspend fun getAdminCredentials(): AdminCredentials? {
        val rememberMe = localStorage[KEY_REMEMBER_ME] == "true"
        if (!rememberMe) return null
        
        val email = localStorage[KEY_ADMIN_EMAIL]
        val passcode = localStorage[KEY_ADMIN_PASSCODE]
        
        if (email.isNullOrBlank() || passcode.isNullOrBlank()) {
            return null
        }
        
        return AdminCredentials(email, passcode, rememberMe)
    }
    
    override suspend fun clearAdminCredentials() {
        localStorage.removeItem(KEY_ADMIN_EMAIL)
        localStorage.removeItem(KEY_ADMIN_PASSCODE)
        localStorage.removeItem(KEY_REMEMBER_ME)
    }
}

actual fun getSecureStorage(): SecureStorage {
    return JsSecureStorage()
}
