@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package org.example.project

import cocoapods.FirebaseCore.FIRApp
import platform.Foundation.NSBundle

/**
 * Initialize Firebase on iOS.
 * - Uses GoogleService-Info.plist when available.
 * - If missing, disables Firebase availability to avoid runtime crashes.
 */
actual fun initFirebase(appContext: Any?) {
    if (FIRApp.defaultApp() != null) return

    val bundle = NSBundle.mainBundle
    val plistPath = bundle.pathForResource(name = "GoogleService-Info", ofType = "plist")
    if (plistPath != null) {
        FIRApp.configure()
        FirebaseAvailability.enabled = true
        return
    }

    // No plist: mark unavailable so upper layers can avoid Firestore/Auth usage.
    FirebaseAvailability.enabled = false
}
