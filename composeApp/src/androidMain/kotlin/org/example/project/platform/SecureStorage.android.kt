package org.example.project.platform

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val PREFS_NAME = "goticky_secure_admin_prefs"

class AndroidSecureStorage(private val context: Context) : SecureStorage {
    
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    
    private val encryptedPrefs: SharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
    
    private fun getEmailKey(email: String) = "email_${email.trim().lowercase()}"
    private fun getPasscodeKey(email: String) = "passcode_${email.trim().lowercase()}"
    private fun getRememberMeKey(email: String) = "remember_${email.trim().lowercase()}"
    
    override suspend fun saveAdminCredentials(email: String, passcode: String, rememberMe: Boolean) = withContext(Dispatchers.IO) {
        val emailKey = getEmailKey(email)
        val passcodeKey = getPasscodeKey(email)
        val rememberKey = getRememberMeKey(email)
        
        encryptedPrefs.edit().apply {
            if (rememberMe) {
                putString(emailKey, email)
                putString(passcodeKey, passcode)
                putBoolean(rememberKey, true)
            } else {
                // If rememberMe is false, clear stored credentials for this email
                remove(emailKey)
                remove(passcodeKey)
                remove(rememberKey)
            }
        }.apply()
    }
    
    override suspend fun getAdminCredentials(email: String): AdminCredentials? = withContext(Dispatchers.IO) {
        val rememberKey = getRememberMeKey(email)
        val rememberMe = encryptedPrefs.getBoolean(rememberKey, false)
        if (!rememberMe) return@withContext null
        
        val emailKey = getEmailKey(email)
        val passcodeKey = getPasscodeKey(email)
        
        val storedEmail = encryptedPrefs.getString(emailKey, null)
        val passcode = encryptedPrefs.getString(passcodeKey, null)
        
        if (storedEmail.isNullOrBlank() || passcode.isNullOrBlank()) {
            return@withContext null
        }
        
        AdminCredentials(storedEmail, passcode, rememberMe)
    }
    
    override suspend fun clearAdminCredentials(email: String) = withContext(Dispatchers.IO) {
        if (email.isBlank()) {
            // Clear all admin credentials
            encryptedPrefs.edit().clear().apply()
        } else {
            // Clear credentials for specific email
            val emailKey = getEmailKey(email)
            val passcodeKey = getPasscodeKey(email)
            val rememberKey = getRememberMeKey(email)
            
            encryptedPrefs.edit().apply {
                remove(emailKey)
                remove(passcodeKey)
                remove(rememberKey)
            }.apply()
        }
    }
    
    override suspend fun hasCredentialsForEmail(email: String): Boolean = withContext(Dispatchers.IO) {
        val rememberKey = getRememberMeKey(email)
        encryptedPrefs.getBoolean(rememberKey, false)
    }
}

actual fun getSecureStorage(): SecureStorage {
    val context = getAndroidContext()
    return AndroidSecureStorage(context)
}

// Get the application context from FirebaseInit's InitState
private fun getAndroidContext(): Context {
    return org.example.project.InitState.context
        ?: throw IllegalStateException("Application context not initialized. Call initFirebase first.")
}
