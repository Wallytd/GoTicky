package org.example.project

// Simple wasm/JS implementation using the host Date API.
// This keeps behavior consistent with the JS target.
actual fun currentHourOfDay(): Int {
    val date = js("new Date()") as kotlin.js.Date
    return date.getHours().toInt()
}
