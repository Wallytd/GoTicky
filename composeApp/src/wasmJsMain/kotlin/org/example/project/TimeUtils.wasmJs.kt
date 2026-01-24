package org.example.project

import kotlinx.datetime.Instant

// Simple wasm/JS implementation using the host Date API.
// This keeps behavior consistent with the JS target.
actual fun currentHourOfDay(): Int {
    val date = js("new Date()") as kotlin.js.Date
    return date.getHours().toInt()
}

actual fun currentInstant(): Instant {
    val date = js("new Date()") as kotlin.js.Date
    val epochMillis = date.getTime().toLong()
    return Instant.fromEpochMilliseconds(epochMillis)
}

actual fun formatTime(timestamp: Long): String {
    val date = js("new Date($timestamp)") as kotlin.js.Date
    val hours = date.getHours().toString().padStart(2, '0')
    val minutes = date.getMinutes().toString().padStart(2, '0')
    return "$hours:$minutes"
}

actual fun formatDate(timestamp: Long): String {
    val date = js("new Date($timestamp)") as kotlin.js.Date
    val day = date.getDate().toString().padStart(2, '0')
    val month = (date.getMonth() + 1).toString().padStart(2, '0')
    val year = date.getFullYear()
    return "$day/$month/$year"
}
