package org.example.project

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    // Ensure Firebase is initialized before any composables access it.
    initFirebase()
    ComposeViewport {
        App()
    }
}