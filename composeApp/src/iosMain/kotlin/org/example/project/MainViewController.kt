package org.example.project

import androidx.compose.ui.window.ComposeUIViewController
import org.example.project.analytics.Analytics
import org.example.project.analytics.AnalyticsEvent
import org.example.project.analytics.AnalyticsLogger
import platform.Foundation.NSLog

fun MainViewController() = ComposeUIViewController {
    // Lightweight iOS logger to keep analytics calls safe.
    Analytics.logger = AnalyticsLogger { event ->
        NSLog("[Analytics][iOS] %@ %@", event.name, event.params.toString())
    }
    App()
}