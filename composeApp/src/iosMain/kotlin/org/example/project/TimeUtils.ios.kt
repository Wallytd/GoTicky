package org.example.project

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
