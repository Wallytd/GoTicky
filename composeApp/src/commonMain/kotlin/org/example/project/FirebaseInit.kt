package org.example.project

// Platform-specific Firebase initialization.
// On Android you must pass an android.content.Context (application context recommended).
// Other platforms can no-op or use their own initialization needs.
expect fun initFirebase(appContext: Any? = null)
