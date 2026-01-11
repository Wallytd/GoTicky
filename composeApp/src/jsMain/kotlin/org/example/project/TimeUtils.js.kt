@file:OptIn(kotlin.time.ExperimentalTime::class)

package org.example.project

import kotlinx.datetime.Instant

actual fun currentHourOfDay(): Int {
    val date = js("new Date()") as kotlin.js.Date
    return date.getHours().toInt()
}

actual fun currentInstant(): Instant {
    val date = js("new Date()") as kotlin.js.Date
    val epochMillis = date.getTime().toLong()
    return Instant.fromEpochMilliseconds(epochMillis)
}
