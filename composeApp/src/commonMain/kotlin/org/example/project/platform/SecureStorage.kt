package org.example.project.platform

/**
 * Platform-specific secure storage for sensitive data like admin credentials.
 * This storage is device-local and never synced to the cloud.
 * 
 * HYBRID APPROACH:
 * - RememberMe preference: Stored in Firestore (users/{userId}/app_settings/preferences)
 * - Actual credentials (email + passcode): Stored locally on device only (encrypted)
 * 
 * This allows:
 * - User preference to sync across their devices
 * - Credentials to remain secure and device-specific
 * - Each device requires re-entry of credentials, but honors the rememberMe preference
 */
interface SecureStorage {
    /**
     * Store admin credentials securely on this device only.
     * The actual passcode is NEVER sent to cloud - only stored locally encrypted.
     * @param email Admin email
     * @param passcode Admin passcode (stored locally, never in cloud)
     * @param rememberMe Whether to remember these credentials on this device
     */
    suspend fun saveAdminCredentials(email: String, passcode: String, rememberMe: Boolean)
    
    /**
     * Retrieve admin credentials from secure storage on this device.
     * Only returns credentials if they were previously saved on THIS device.
     * @param email The admin email to look up credentials for
     * @return AdminCredentials if credentials exist for this email on this device, null otherwise
     */
    suspend fun getAdminCredentials(email: String): AdminCredentials?
    
    /**
     * Clear admin credentials from secure storage on this device.
     * @param email The admin email whose credentials should be cleared (if empty, clears all)
     */
    suspend fun clearAdminCredentials(email: String = "")
    
    /**
     * Check if credentials exist for a specific admin email on this device.
     */
    suspend fun hasCredentialsForEmail(email: String): Boolean
}

data class AdminCredentials(
    val email: String,
    val passcode: String,
    val rememberMe: Boolean
)

/**
 * Get platform-specific secure storage implementation.
 */
expect fun getSecureStorage(): SecureStorage
