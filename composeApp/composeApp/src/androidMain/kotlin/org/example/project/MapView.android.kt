package org.example.project

import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.CameraUpdateFactory
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
    selected: Pair<Double, Double>?,
    onMapClick: ((Double, Double) -> Unit)?,
    liveUpdates: Boolean,
) {
    val context = LocalContext.current
    val mapsApiKey = remember {
        runCatching {
            val appInfo = if (Build.VERSION.SDK_INT >= 33) {
                context.packageManager.getApplicationInfo(
                    context.packageName,
                    PackageManager.ApplicationInfoFlags.of(PackageManager.GET_META_DATA.toLong())
                )
            } else {
                @Suppress("DEPRECATION")
                context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            }
            appInfo.metaData?.getString("com.google.android.geo.API_KEY").orEmpty()
        }.getOrDefault("")
    }

    val initialTarget = selected?.let { LatLng(it.first, it.second) }
        ?: events.firstOrNull()?.let { LatLng(it.lat, it.lng) }
        ?: LatLng(0.0, 0.0)
    val initialZoom = if (selected != null) 15f else if (events.isNotEmpty()) 10f else 2f
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialTarget, initialZoom)
    }

    val mapEvents = remember { mutableStateListOf<MapEvent>().apply { addAll(events) } }
    var selectedLatLng by remember { mutableStateOf(selected) }

    LaunchedEffect(events, liveUpdates) {
        if (!liveUpdates) {
            mapEvents.clear()
            mapEvents.addAll(events)
        }
    }

    LaunchedEffect(selected) {
        selectedLatLng = selected
    }

    DisposableEffect(liveUpdates) {
        val firestore = try {
            FirebaseFirestore.getInstance()
        } catch (_: Exception) {
            null
        }
        var registration: ListenerRegistration? = null
        if (liveUpdates && firestore != null) {
            registration = firestore.collection("events")
                .whereEqualTo("isApproved", true)
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
        val focusLatLng = selectedLatLng?.let { LatLng(it.first, it.second) }
            ?: mapEvents.firstOrNull()?.let { LatLng(it.lat, it.lng) }
            ?: initialTarget
        val focusZoom = if (selectedLatLng != null) 16f else if (mapEvents.isNotEmpty()) 10f else 2f

        LaunchedEffect(focusLatLng) {
            runCatching {
                cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(focusLatLng, focusZoom))
            }
        }

        Box(modifier = modifier) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                onMapClick = { latLng ->
                    selectedLatLng = latLng.latitude to latLng.longitude
                    onMapClick?.invoke(latLng.latitude, latLng.longitude)
                }
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
                selectedLatLng?.let { (lat, lng) ->
                    val position = LatLng(lat, lng)
                    Marker(
                        state = MarkerState(position),
                        title = "Selected location",
                        snippet = "Tap map again to adjust",
                        onClick = { false }
                    )
                }
            }

            if (mapsApiKey.isBlank()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Google Maps API key is missing. Set MAPS_API_KEY in gradle.properties or as an environment variable, then rebuild.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
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
