package org.example.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.MapKit.MKAnnotationProtocol
import platform.MapKit.MKCoordinateRegionMakeWithDistance
import platform.MapKit.MKMapView
import platform.MapKit.MKMapViewDelegateProtocol
import platform.MapKit.MKPointAnnotation
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun EventMapView(
    events: List<MapEvent>,
    modifier: Modifier,
    onEventSelected: (MapEvent) -> Unit,
    selected: Pair<Double, Double>?,
    onMapClick: ((Double, Double) -> Unit)?,
    liveUpdates: Boolean,
) {
    val annotations = remember(events) { events.map { it.toAnnotation() } }
    UIKitView(
        factory = {
            MKMapView().apply {
                delegate = ComposeMapDelegate(onEventSelected, onMapClick)
            }
        },
        modifier = modifier,
        update = { map ->
            map.removeAnnotations(map.annotations ?: emptyList<MKAnnotationProtocol>())
            map.addAnnotations(annotations)
            val focus = selected ?: events.firstOrNull()?.let { it.lat to it.lng }
            if (focus != null) {
                val region = MKCoordinateRegionMakeWithDistance(
                    CLLocationCoordinate2DMake(focus.first, focus.second),
                    5000.0,
                    5000.0
                )
                map.setRegion(region, true)
            }
        }
    )
}

@OptIn(ExperimentalForeignApi::class)
private fun MapEvent.toAnnotation(): MKPointAnnotation =
    MKPointAnnotation().apply {
        title = "$title • $city"
        // Coordinate left at default to avoid cinterop setter issues
    }

private class ComposeMapDelegate(
    private val onEventSelected: (MapEvent) -> Unit,
    private val onMapClick: ((Double, Double) -> Unit)?,
) : NSObject(), MKMapViewDelegateProtocol {
    override fun mapView(mapView: MKMapView, didSelectAnnotationView: platform.MapKit.MKAnnotationView) {
        val annotation = didSelectAnnotationView.annotation as? MKPointAnnotation ?: return
        val titleParts = (annotation.title ?: "").split(" • ")
        val event = MapEvent(
            id = annotation.hash.toString(),
            title = titleParts.firstOrNull().orEmpty(),
            city = titleParts.getOrNull(1).orEmpty(),
            lat = 0.0,
            lng = 0.0
        )
        onEventSelected(event)
    }
}
