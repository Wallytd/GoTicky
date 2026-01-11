package org.example.project.analytics

/** Lightweight analytics abstraction for KMP. */
data class AnalyticsEvent(
    val name: String,
    val params: Map<String, String> = emptyMap(),
)

fun interface AnalyticsLogger {
    fun log(event: AnalyticsEvent)
}

object Analytics {
    /** Replaceable logger; defaults to a debug console logger. */
    var logger: AnalyticsLogger = DebugAnalyticsLogger()

    fun log(event: AnalyticsEvent) {
        logger.log(event)
    }
}

private class DebugAnalyticsLogger : AnalyticsLogger {
    override fun log(event: AnalyticsEvent) {
        // Simple println for now; platforms can override Analytics.logger with a real backend.
        println("[Analytics] ${'$'}{event.name} ${'$'}{event.params}")
    }
}
