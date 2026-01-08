package org.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * iOS placeholder implementation that lists available events instead of rendering MapKit.
 * This keeps the iOS target building cleanly on non-mac hosts.
 */
@Composable
actual fun EventMapView(
    events: List<MapEvent>,
    modifier: Modifier,
    onEventSelected: (MapEvent) -> Unit,
    selected: Pair<Double, Double>?,
    onMapClick: ((Double, Double) -> Unit)?,
    liveUpdates: Boolean,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Map preview not available in this build.\nSelect an event below:",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(12.dp)
        )
        events.forEach { event ->
            Text(
                text = "${event.title} • ${event.city}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable { onEventSelected(event) }
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}
