package org.example.project

import java.time.Instant as JavaInstant
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.Clock as JavaClock
import kotlinx.datetime.Instant
import kotlin.time.ExperimentalTime

actual fun currentHourOfDay(): Int = LocalTime.now().hour

@OptIn(ExperimentalTime::class)
actual fun currentInstant(): Instant {
    val javaInstant = JavaClock.systemUTC().instant()
    return Instant.fromEpochMilliseconds(javaInstant.toEpochMilli())
}

actual fun formatTime(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.systemDefault())
    return formatter.format(JavaInstant.ofEpochMilli(timestamp))
}

actual fun formatDate(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.systemDefault())
    return formatter.format(JavaInstant.ofEpochMilli(timestamp))
}
