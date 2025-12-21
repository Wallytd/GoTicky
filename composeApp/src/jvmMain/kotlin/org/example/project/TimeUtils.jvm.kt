package org.example.project

import java.time.LocalTime
import java.time.Clock as JavaClock
import kotlinx.datetime.Instant

actual fun currentHourOfDay(): Int = LocalTime.now().hour

actual fun currentInstant(): Instant {
    val javaInstant = JavaClock.systemUTC().instant()
    return Instant.fromEpochMilliseconds(javaInstant.toEpochMilli())
}
