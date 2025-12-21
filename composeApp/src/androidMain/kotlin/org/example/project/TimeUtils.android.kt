package org.example.project

import java.util.Calendar
import java.time.Clock as JavaClock
import kotlinx.datetime.Instant

actual fun currentHourOfDay(): Int {
    val cal = Calendar.getInstance()
    return cal.get(Calendar.HOUR_OF_DAY)
}

@kotlin.OptIn(kotlin.time.ExperimentalTime::class)
actual fun currentInstant(): Instant {
    val javaInstant = JavaClock.systemUTC().instant()
    return Instant.fromEpochMilliseconds(javaInstant.toEpochMilli())
}
