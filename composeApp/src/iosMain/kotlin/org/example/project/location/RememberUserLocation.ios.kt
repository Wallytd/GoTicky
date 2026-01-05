package org.example.project.location

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLLocationAccuracyHundredMeters
import platform.Foundation.NSError
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun rememberUserLocation(): State<GeoPoint?> {
    val locationState = remember { mutableStateOf<GeoPoint?>(null) }
    val manager = remember {
        CLLocationManager().apply {
            desiredAccuracy = kCLLocationAccuracyHundredMeters
        }
    }

    DisposableEffect(manager) {
        val delegate = object : NSObject(), CLLocationManagerDelegateProtocol {
            override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                val last = didUpdateLocations.lastOrNull() as? CLLocation ?: return
                val coordinate = last.coordinate
                coordinate.useContents {
                    locationState.value = GeoPoint(
                        lat = latitude,
                        lng = longitude
                    )
                }
            }

            override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
                // Keep silent; state remains previous/null.
            }
        }

        manager.delegate = delegate
        manager.requestWhenInUseAuthorization()
        manager.startUpdatingLocation()

        onDispose {
            manager.stopUpdatingLocation()
            manager.delegate = null
        }
    }

    return locationState
}
