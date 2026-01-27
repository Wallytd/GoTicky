package org.example.project.location

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult

@Composable
actual fun rememberUserLocation(): State<GeoPoint?> {
    val context = LocalContext.current
    val locationState = remember { mutableStateOf<GeoPoint?>(null) }
    val fused = remember { LocationServices.getFusedLocationProviderClient(context) }
    val requestedPermissions = remember { mutableStateOf(false) }
    val permissionsGranted = remember { mutableStateOf(false) }
    val locationManager = remember {
        context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
    }

    fun hasPermission(): Boolean {
        val hasCoarse = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasFine = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        return hasCoarse || hasFine
    }

    fun updateFromLocation(lat: Double, lng: Double) {
        val isValid = lat in -90.0..90.0 && lng in -180.0..180.0
        if (isValid) {
            locationState.value = GeoPoint(lat, lng)
        }
    }

    fun refreshLastKnown() {
        if (!hasPermission()) {
            locationState.value = null
            return
        }
        runCatching {
            fused.getCurrentLocation(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                null
            ).addOnSuccessListener { loc ->
                if (loc != null) {
                    updateFromLocation(loc.latitude, loc.longitude)
                }
            }
        }
        runCatching {
            fused.lastLocation.addOnSuccessListener { loc ->
                if (loc != null) {
                    updateFromLocation(loc.latitude, loc.longitude)
                }
            }
        }
    }

    val locationCallback = remember {
        object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { loc ->
                    updateFromLocation(loc.latitude, loc.longitude)
                }
            }

            override fun onLocationAvailability(availability: LocationAvailability) {
                if (!availability.isLocationAvailable) {
                    locationState.value = null
                }
            }
        }
    }

    val providerReceiver = remember {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == LocationManager.PROVIDERS_CHANGED_ACTION) {
                    val enabled = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true ||
                        locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true
                    if (enabled && hasPermission()) {
                        refreshLastKnown()
                    } else {
                        locationState.value = null
                    }
                }
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val granted = result[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            result[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        permissionsGranted.value = granted
        if (granted) {
            refreshLastKnown()
        } else {
            locationState.value = null
        }
    }

    LaunchedEffect(context) {
        val granted = hasPermission()
        permissionsGranted.value = granted
        if (!granted) {
            if (!requestedPermissions.value) {
                requestedPermissions.value = true
                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
            return@LaunchedEffect
        }
        refreshLastKnown()
    }

    DisposableEffect(permissionsGranted.value) {
        var receiverRegistered = false
        if (permissionsGranted.value) {
            val request = LocationRequest.Builder(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                5_000L
            )
                .setMinUpdateIntervalMillis(2_500L)
                .setMaxUpdateDelayMillis(7_500L)
                .build()

            runCatching {
                fused.requestLocationUpdates(
                    request,
                    locationCallback,
                    Looper.getMainLooper()
                )
            }
            runCatching {
                context.registerReceiver(
                    providerReceiver,
                    IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
                )
                receiverRegistered = true
            }
        } else {
            locationState.value = null
        }

        onDispose {
            runCatching { fused.removeLocationUpdates(locationCallback) }
            if (receiverRegistered) {
                runCatching { context.unregisterReceiver(providerReceiver) }
            }
        }
    }

    return locationState
}
