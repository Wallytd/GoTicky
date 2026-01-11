package org.example.project

import kotlin.test.Test
import kotlin.test.assertEquals
import org.example.project.analytics.Analytics
import org.example.project.analytics.AnalyticsEvent
import org.example.project.analytics.AnalyticsLogger

class ComposeAppCommonTest {

    @Test
    fun example() {
        assertEquals(3, 1 + 2)
    }

    @Test
    fun analyticsLoggerReceivesEvents() {
        val recorded = mutableListOf<AnalyticsEvent>()
        val original = Analytics.logger
        try {
            Analytics.logger = AnalyticsLogger { event -> recorded.add(event) }
            Analytics.log(AnalyticsEvent(name = "test_event", params = mapOf("k" to "v")))
            assertEquals(1, recorded.size)
            assertEquals("test_event", recorded[0].name)
            assertEquals("v", recorded[0].params["k"])
        } finally {
            Analytics.logger = original
        }
    }
}