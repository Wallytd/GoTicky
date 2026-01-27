package org.example.project

/**
 * Simple flag to reflect whether Firebase is configured for the current platform.
 * iOS sets this to false when GoogleService-Info.plist is missing to avoid runtime crashes.
 */
object FirebaseAvailability {
    var enabled: Boolean = true
        internal set
}
