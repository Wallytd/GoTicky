package org.example.project

actual fun currentHourOfDay(): Int {
    val date = js("new Date()") as kotlin.js.Date
    return date.getHours().toInt()
}
