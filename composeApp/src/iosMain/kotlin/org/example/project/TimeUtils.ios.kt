@file:OptIn(kotlin.time.ExperimentalTime::class, kotlinx.cinterop.ExperimentalForeignApi::class)

package org.example.project

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitHour
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

actual fun currentHourOfDay(): Int {
    val calendar = NSCalendar.currentCalendar()
    val date = NSDate()
    val hour = calendar.component(NSCalendarUnitHour, fromDate = date).toInt()
    return hour
}

actual fun currentInstant(): Instant {
    // Foundation provides seconds since epoch as Double; convert to millis for Instant.
    val epochMillis = (NSDate().timeIntervalSince1970 * 1000.0).toLong()
    return Instant.fromEpochMilliseconds(epochMillis)
}
