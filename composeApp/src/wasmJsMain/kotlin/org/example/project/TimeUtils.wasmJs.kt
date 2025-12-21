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
