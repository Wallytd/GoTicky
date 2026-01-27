package org.example.project.platform

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val PREFS_NAME = "goticky_secure_admin_prefs"
private const val KEY_ADMIN_EMAIL = "admin_email"
private const val KEY_ADMIN_PASSCODE = "admin_passcode"
private const val KEY_REMEMBER_ME = "remember_me"

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
    
    override suspend fun saveAdminCredentials(email: String, passcode: String, rememberMe: Boolean) = withContext(Dispatchers.IO) {
        encryptedPrefs.edit().apply {
            if (rememberMe) {
                putString(KEY_ADMIN_EMAIL, email)
                putString(KEY_ADMIN_PASSCODE, passcode)
                putBoolean(KEY_REMEMBER_ME, true)
            } else {
                // If rememberMe is false, clear stored credentials
                remove(KEY_ADMIN_EMAIL)
                remove(KEY_ADMIN_PASSCODE)
                putBoolean(KEY_REMEMBER_ME, false)
            }
        }.apply()
    }
    
    override suspend fun getAdminCredentials(): AdminCredentials? = withContext(Dispatchers.IO) {
        val rememberMe = encryptedPrefs.getBoolean(KEY_REMEMBER_ME, false)
        if (!rememberMe) return@withContext null
        
        val email = encryptedPrefs.getString(KEY_ADMIN_EMAIL, null)
        val passcode = encryptedPrefs.getString(KEY_ADMIN_PASSCODE, null)
        
        if (email.isNullOrBlank() || passcode.isNullOrBlank()) {
            return@withContext null
        }
        
        AdminCredentials(email, passcode, rememberMe)
    }
    
    override suspend fun clearAdminCredentials() = withContext(Dispatchers.IO) {
        encryptedPrefs.edit().apply {
            remove(KEY_ADMIN_EMAIL)
            remove(KEY_ADMIN_PASSCODE)
            remove(KEY_REMEMBER_ME)
        }.apply()
    }
}

actual fun getSecureStorage(): SecureStorage {
    val context = getAndroidContext()
    return AndroidSecureStorage(context)
}

// Get the application context from FirebaseInit's InitState
private fun getAndroidContext(): Context {
    // Access the context stored by FirebaseInit
    val contextField = Class.forName("org.example.project.FirebaseInit_androidKt\$InitState")
        .getDeclaredField("context")
    contextField.isAccessible = true
    val initStateInstance = Class.forName("org.example.project.FirebaseInit_androidKt\$InitState")
        .getDeclaredField("INSTANCE")
        .apply { isAccessible = true }
        .get(null)
    return (contextField.get(initStateInstance) as? Context)
        ?: throw IllegalStateException("Application context not initialized. Call initFirebase first.")
}
