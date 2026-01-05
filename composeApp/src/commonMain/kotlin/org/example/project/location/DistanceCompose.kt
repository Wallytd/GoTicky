package org.example.project.location

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.example.project.data.DistanceSample
import org.example.project.data.EventItem
import org.example.project.data.NearbyEvent

fun eventLocationGeoPoint(event: EventItem): GeoPoint? = when (event.city.lowercase()) {
    "harare" -> GeoPoint(-17.8292, 31.0522)
    "bulawayo" -> GeoPoint(-20.1325, 28.6265)
    "gaborone" -> GeoPoint(-24.6282, 25.9231)
    "victoria falls" -> GeoPoint(-17.9243, 25.8562)
    "maun" -> GeoPoint(-19.9833, 23.4167)
    "francistown" -> GeoPoint(-21.17, 27.5072)
    else -> null
}

private fun formatDistanceKm(distanceKm: Double?): Double? =
    distanceKm?.let { kotlin.math.round(it * 10.0) / 10.0 }

/**
 * Returns per-event distance using live user location when available, otherwise falls back to sample distances.
 */
@Composable
fun rememberDistanceForEvents(events: List<EventItem>): Map<String, DistanceSample> {
    val userLocationState = rememberUserLocation()
    val userLocation = userLocationState.value

    return remember(userLocation, events) {
        events.associate { event ->
            val geo = eventLocationGeoPoint(event)
            val distanceKm = if (userLocation != null && geo != null) {
                haversineDistanceKm(userLocation, geo)
            } else {
                null
            }
            val roundedKm = formatDistanceKm(distanceKm)
            val sample = DistanceSample(
                eventId = event.id,
                fromLabel = if (userLocation != null) "Your location" else "Distance unavailable",
                distanceKm = roundedKm ?: -1.0,
            )
            event.id to sample
        }
    }
}

@Composable
fun rememberNearbyEvents(events: List<EventItem>): List<NearbyEvent> {
    val distances = rememberDistanceForEvents(events)
    return remember(distances) {
        events.mapNotNull { event ->
            distances[event.id]?.let { dist -> NearbyEvent(event, dist) }
        }
    }
}
