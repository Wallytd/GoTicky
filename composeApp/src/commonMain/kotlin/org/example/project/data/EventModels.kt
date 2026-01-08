@file:OptIn(kotlin.time.ExperimentalTime::class)

package org.example.project.data

import kotlinx.datetime.Instant
import org.example.project.ui.theme.IconCategory

data class EventItem(
    val id: String,
    val title: String,
    val city: String,
    val dateLabel: String,
    val startsAt: Instant? = null,
    val priceFrom: String,
    val category: IconCategory,
    val badge: String? = null,
    val tag: String? = null,
    val month: String = "January",
    val imagePath: String? = null,
    val lat: Double? = null,
    val lng: Double? = null,
)

data class DistanceSample(
    val eventId: String,
    val fromLabel: String,
    val distanceKm: Double,
) {
    val formatted: String
        get() = if (distanceKm < 0) {
            fromLabel.ifBlank { "Distance unavailable" }
        } else {
            "${((distanceKm * 10.0).toInt() / 10.0)} km away"
        }
}

data class NearbyEvent(
    val event: EventItem,
    val distance: DistanceSample,
)
