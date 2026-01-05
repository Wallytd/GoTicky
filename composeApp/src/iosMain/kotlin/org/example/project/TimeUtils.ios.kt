@file:OptIn(kotlin.time.ExperimentalTime::class, kotlinx.cinterop.ExperimentalForeignApi::class)

package org.example.project

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitHour
import platform.Foundation.NSDate

actual fun currentHourOfDay(): Int {
    val calendar = NSCalendar.currentCalendar()
    val date = NSDate()
    val hour = calendar.component(NSCalendarUnitHour, fromDate = date).toInt()
    return hour
}

actual fun currentInstant(): Instant {
    return Clock.System.now()
}
