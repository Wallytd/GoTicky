package org.example.project.location

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.State

@Composable
actual fun rememberUserLocation(): State<GeoPoint?> =
    remember { mutableStateOf<GeoPoint?>(null) }
