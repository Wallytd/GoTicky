package org.example.project

import android.content.Context
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize

// Android actual: requires a non-null application Context.
actual fun initFirebase(appContext: Any?) {
    if (InitState.initialized) return

    val context = (appContext as? Context)?.applicationContext
        ?: error("initFirebase(appContext) requires an Android Context")

    Firebase.initialize(context)
    InitState.initialized = true
}

private object InitState {
    var initialized: Boolean = false
}
