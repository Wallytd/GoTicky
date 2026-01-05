package org.example.project

import java.util.Calendar
import kotlinx.datetime.Instant

actual fun currentHourOfDay(): Int {
    val cal = Calendar.getInstance()
    return cal.get(Calendar.HOUR_OF_DAY)
}

@kotlin.OptIn(kotlin.time.ExperimentalTime::class)
actual fun currentInstant(): Instant {
    val epochMillis = System.currentTimeMillis()
    return Instant.fromEpochMilliseconds(epochMillis)
}
