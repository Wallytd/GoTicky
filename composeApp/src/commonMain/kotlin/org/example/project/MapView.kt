package org.example.project

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class MapEvent(
    val id: String,
    val title: String,
    val city: String,
    val lat: Double,
    val lng: Double,
)

@Composable
expect fun EventMapView(
    events: List<MapEvent>,
    modifier: Modifier = Modifier,
    onEventSelected: (MapEvent) -> Unit = {},
)
