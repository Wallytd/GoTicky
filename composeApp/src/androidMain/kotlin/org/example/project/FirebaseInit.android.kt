package org.example.project

import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Build
import android.util.Log
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize

// Android actual: requires a non-null application Context.
actual fun initFirebase(appContext: Any?) {
    val context = (appContext as? Context)?.applicationContext ?: InitState.context
    if (context == null) {
        if (InitState.firebaseInitialized) return
        error("initFirebase(appContext) requires an Android Context")
    }

    InitState.context = context

    if (!InitState.firebaseInitialized) {
        Firebase.initialize(context)
        InitState.firebaseInitialized = true
    }

    // App Check temporarily disabled to unblock local/admin flows. Re-enable when ready.
}

private object InitState {
    var firebaseInitialized: Boolean = false
    var context: Context? = null
}

private fun isProbablyEmulator(): Boolean {
    val fingerprint = Build.FINGERPRINT
    val model = Build.MODEL
    val manufacturer = Build.MANUFACTURER
    val brand = Build.BRAND
    val device = Build.DEVICE
    val product = Build.PRODUCT
    return fingerprint.startsWith("generic") ||
        fingerprint.contains("emulator") ||
        fingerprint.contains("unknown") ||
        model.contains("google_sdk") ||
        model.contains("Emulator") ||
        model.contains("Android SDK built for") ||
        manufacturer.contains("Genymotion") ||
        (brand.startsWith("generic") && device.startsWith("generic")) ||
        product.contains("sdk") ||
        product.contains("emulator") ||
        product.contains("simulator")
}
