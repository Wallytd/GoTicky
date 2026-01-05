package org.example.project

/**
 * iOS placeholder: no Firebase pods are wired yet. This keeps initialization safe
 * and crash-free; when GoogleService-Info.plist is added and pods are enabled,
 * replace with a real FirebaseApp.configure() call.
 */
actual fun initFirebase(appContext: Any?) {
    println("Firebase init (iOS stub): skipped – add GoogleService-Info.plist and pods to enable.")
}
