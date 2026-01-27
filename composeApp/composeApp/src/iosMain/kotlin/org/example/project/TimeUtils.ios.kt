@file:OptIn(kotlin.time.ExperimentalTime::class, kotlinx.cinterop.ExperimentalForeignApi::class)

package org.example.project

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitHour
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.NSTimeZone

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

actual fun formatTime(timestamp: Long): String {
    val formatter = NSDateFormatter()
    formatter.locale = NSLocale.currentLocale
    formatter.timeZone = NSTimeZone.localTimeZone
    formatter.dateFormat = "HH:mm"
    val date = NSDate(timeIntervalSince1970 = timestamp.toDouble() / 1000.0)
    return formatter.stringFromDate(date)
}

actual fun formatDate(timestamp: Long): String {
    val formatter = NSDateFormatter()
    formatter.locale = NSLocale.currentLocale
    formatter.timeZone = NSTimeZone.localTimeZone
    formatter.dateFormat = "dd/MM/yyyy"
    val date = NSDate(timeIntervalSince1970 = timestamp.toDouble() / 1000.0)
    return formatter.stringFromDate(date)
}
