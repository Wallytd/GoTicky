package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "GoTicky",
        state = rememberWindowState(placement = WindowPlacement.Maximized),
        undecorated = true,
    ) {
        App()
    }
}