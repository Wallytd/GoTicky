package org.example.project

import kotlin.js.JsModule
import kotlin.js.JsNonModule
import kotlin.js.json

@JsModule("firebase/app")
@JsNonModule
external object FirebaseAppModule {
    val initializeApp: (config: dynamic) -> dynamic
}

private var firebaseInitialized = false

// JS/web: initialize the default Firebase app using the Web config so
// dev.gitlive Firebase (auth/firestore) can bind to it.
actual fun initFirebase(appContext: Any?) {
    if (firebaseInitialized) return

    val config = json(
        "apiKey" to "AIzaSyChz5gjysFraEHiHc-h8AyebGCqab3ddok",
        "authDomain" to "goticky-47533.firebaseapp.com",
        "databaseURL" to "https://goticky-47533-default-rtdb.firebaseio.com",
        "projectId" to "goticky-47533",
        "storageBucket" to "goticky-47533.firebasestorage.app",
        "messagingSenderId" to "1058527094400",
        "appId" to "1:1058527094400:web:aa94c4317e4c1fa5cd6865",
        "measurementId" to "G-CQSHE7JN4P",
    )

    val app = FirebaseAppModule.initializeApp(config)
    js("console.log('GoTicky Firebase JS init', app && app.name)")
    firebaseInitialized = true
}
