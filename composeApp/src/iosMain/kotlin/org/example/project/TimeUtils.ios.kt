package org.example.project

import kotlinx.datetime.Instant
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitHour
import platform.Foundation.NSDate
import platform.Foundation.currentCalendar

actual fun currentHourOfDay(): Int {
    val calendar = NSCalendar.currentCalendar
    val date = NSDate()
    val hour = calendar.component(NSCalendarUnitHour, fromDate = date).toInt()
    return hour
}

actual fun currentInstant(): Instant {
    val date = NSDate()
    val epochSeconds = date.timeIntervalSince1970
    val epochMillis = (epochSeconds * 1000.0).toLong()
    return Instant.fromEpochMilliseconds(epochMillis)
}
