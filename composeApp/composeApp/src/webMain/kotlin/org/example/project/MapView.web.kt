package org.example.project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

// Web placeholder to avoid duplicating the js actual. Keeping a distinct name prevents overload conflicts.
@Composable
fun EventMapViewWebPlaceholder(
    events: List<MapEvent>,
    modifier: Modifier,
    onEventSelected: (MapEvent) -> Unit,
    selected: Pair<Double, Double>?,
    onMapClick: ((Double, Double) -> Unit)?,
    liveUpdates: Boolean,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Interactive map is optimized for the mobile app in this demo.")
    }
}
