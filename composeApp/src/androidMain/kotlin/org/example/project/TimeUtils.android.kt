package org.example.project

import java.time.Instant as JavaInstant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
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

actual fun formatTime(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.systemDefault())
    return formatter.format(JavaInstant.ofEpochMilli(timestamp))
}

actual fun formatDate(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.systemDefault())
    return formatter.format(JavaInstant.ofEpochMilli(timestamp))
}
