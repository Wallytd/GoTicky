package org.example.project.location

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices

@Composable
actual fun rememberUserLocation(): State<GeoPoint?> {
    val context = LocalContext.current
    val locationState = remember { mutableStateOf<GeoPoint?>(null) }
    val fused = remember { LocationServices.getFusedLocationProviderClient(context) }
    val requested = remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val granted = result[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            result[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) {
            fused.lastLocation.addOnSuccessListener { loc ->
                if (loc != null) {
                    locationState.value = GeoPoint(loc.latitude, loc.longitude)
                }
            }
        }
    }

    LaunchedEffect(context) {
        val hasCoarse = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasFine = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasCoarse && !hasFine) {
            if (!requested.value) {
                requested.value = true
                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
            return@LaunchedEffect
        }

        runCatching {
            fused.lastLocation
                .addOnSuccessListener { loc ->
                    if (loc != null) {
                        locationState.value = GeoPoint(loc.latitude, loc.longitude)
                    }
                }
        }
    }

    return locationState
}
