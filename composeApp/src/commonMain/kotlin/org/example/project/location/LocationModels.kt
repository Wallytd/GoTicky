package org.example.project.location

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.PI

data class GeoPoint(val lat: Double, val lng: Double)

/**
 * Returns a remembered state that updates with the user's current location if available.
 * On platforms without location support or permission, the state will remain null.
 */
@Composable
expect fun rememberUserLocation(): State<GeoPoint?>

/**
 * Great-circle distance in kilometers using the haversine formula.
 */
fun haversineDistanceKm(a: GeoPoint, b: GeoPoint): Double {
    val earthRadiusKm = 6371.0
    val dLat = (b.lat - a.lat).degToRad()
    val dLng = (b.lng - a.lng).degToRad()
    val lat1 = a.lat.degToRad()
    val lat2 = b.lat.degToRad()

    val h = sin(dLat / 2).let { it * it } +
        cos(lat1) * cos(lat2) * sin(dLng / 2).let { it * it }
    val c = 2 * atan2(sqrt(h), sqrt(1 - h))
    return earthRadiusKm * c
}

private fun Double.degToRad(): Double = this * (PI / 180.0)
