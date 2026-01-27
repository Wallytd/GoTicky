package org.example.project.platform

/**
 * Platform-specific secure storage for sensitive data like admin credentials.
 * This storage is device-local and never synced to the cloud.
 */
interface SecureStorage {
    /**
     * Store admin credentials securely on this device only.
     * @param email Admin email
     * @param passcode Admin passcode
     * @param rememberMe Whether to remember these credentials
     */
    suspend fun saveAdminCredentials(email: String, passcode: String, rememberMe: Boolean)
    
    /**
     * Retrieve admin credentials from secure storage on this device.
     * @return AdminCredentials if rememberMe was true and credentials exist, null otherwise
     */
    suspend fun getAdminCredentials(): AdminCredentials?
    
    /**
     * Clear admin credentials from secure storage on this device.
     */
    suspend fun clearAdminCredentials()
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
