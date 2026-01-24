package org.example.project

import kotlinx.datetime.Instant
import kotlin.js.Date

actual fun currentHourOfDay(): Int {
    val date = Date()
    return date.getHours().toInt()
}

@kotlin.OptIn(kotlin.time.ExperimentalTime::class)
actual fun currentInstant(): Instant {
    val now = Date()
    return Instant.fromEpochMilliseconds(now.getTime().toLong())
}

actual fun formatTime(timestamp: Long): String {
    val date = Date(timestamp)
    val hours = date.getHours().toString().padStart(2, '0')
    val minutes = date.getMinutes().toString().padStart(2, '0')
    return "$hours:$minutes"
}

actual fun formatDate(timestamp: Long): String {
    val date = Date(timestamp)
    val day = date.getDate().toString().padStart(2, '0')
    val month = (date.getMonth() + 1).toString().padStart(2, '0')
    val year = date.getFullYear()
    return "$day/$month/$year"
}
