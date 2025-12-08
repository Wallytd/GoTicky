package org.example.project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
actual fun EventMapView(
    events: List<MapEvent>,
    modifier: Modifier,
    onEventSelected: (MapEvent) -> Unit,
) {
    val initialTarget = events.firstOrNull()?.let { LatLng(it.lat, it.lng) } ?: LatLng(0.0, 0.0)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialTarget, 4f)
    }

    val mapEvents = remember { mutableStateListOf<MapEvent>().apply { addAll(events) } }

    DisposableEffect(Unit) {
        val firestore = try {
            FirebaseFirestore.getInstance()
        } catch (_: Exception) {
            null
        }
        var registration: ListenerRegistration? = null
        if (firestore != null) {
            registration = firestore.collection("events")
                .addSnapshotListener { snapshot, error ->
                    if (error != null || snapshot == null) return@addSnapshotListener
                    val remoteEvents = snapshot.documents.mapNotNull { doc ->
                        val id = doc.id
                        val title = doc.getString("title") ?: return@mapNotNull null
                        val city = doc.getString("city") ?: ""
                        val lat = doc.getDouble("lat") ?: return@mapNotNull null
                        val lng = doc.getDouble("lng") ?: return@mapNotNull null
                        MapEvent(id = id, title = title, city = city, lat = lat, lng = lng)
                    }
                    if (remoteEvents.isNotEmpty()) {
                        mapEvents.clear()
                        mapEvents.addAll(remoteEvents)
                    }
                }
        }
        onDispose {
            registration?.remove()
        }
    }

    if (mapEvents.isNotEmpty()) {
        GoogleMap(
            modifier = modifier,
            cameraPositionState = cameraPositionState,
        ) {
            mapEvents.forEach { event ->
                val position = LatLng(event.lat, event.lng)
                Marker(
                    state = MarkerState(position),
                    title = event.title,
                    snippet = event.city,
                    onClick = {
                        onEventSelected(event)
                        false
                    }
                )
            }
        }
    } else {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Map view is currently unavailable. You can still explore events from the list.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
