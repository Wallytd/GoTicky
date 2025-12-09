package org.example.project

import java.util.Calendar

actual fun currentHourOfDay(): Int {
    val cal = Calendar.getInstance()
    return cal.get(Calendar.HOUR_OF_DAY)
}
