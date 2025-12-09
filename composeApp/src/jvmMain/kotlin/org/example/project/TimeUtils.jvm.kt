package org.example.project

import java.time.LocalTime

actual fun currentHourOfDay(): Int = LocalTime.now().hour
