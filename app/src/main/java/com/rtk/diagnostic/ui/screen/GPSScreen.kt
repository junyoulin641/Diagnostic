package com.rtk.diagnostic.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rtk.diagnostic.ui.components.GpsScreenContent
import com.rtk.diagnostic.utils.LocationPermissionHandler
import com.rtk.diagnostic.utils.RequestLocationPermission
import com.rtk.diagnostic.viewmodel.GpsViewModel

@Composable
fun GPSScreen(hasSignal: Boolean = true) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val locationPermissionHandler = remember { LocationPermissionHandler(context) }
    val viewModel: GpsViewModel = viewModel()
    val strNmeaData by viewModel.strNmeaData.collectAsState("")
    val listGpsInformation by viewModel.listGpsInformation.collectAsState()
    var displayText by remember { mutableStateOf(strNmeaData) }
    var bLocationPermission by remember { mutableStateOf(locationPermissionHandler.hasLocationPermission()) }
    var bGpsEnabled by remember { mutableStateOf(locationPermissionHandler.isGpsEnabled()) }
    DisposableEffect(lifecycleOwner, bLocationPermission, bGpsEnabled) {
        if (bLocationPermission && bGpsEnabled)
        {
            viewModel.startNmeaListening()
        }
        onDispose {
        }
    }
    when {
        !bLocationPermission -> {
            RequestLocationPermission(
                onPermissionGranted = {
                    bLocationPermission = true
                    bGpsEnabled = locationPermissionHandler.isGpsEnabled()
                    if (bGpsEnabled)
                    {
                        viewModel.startNmeaListening()
                    }
                },
                onPermissionDenied = {
                    bLocationPermission = false
                }
            )
            displayText="Location permission is required for NMEA data"
        }
        !bGpsEnabled ->{
            displayText="Please enable location services in system settings"
        }
        else -> {
            displayText = strNmeaData
        }
    }
    GpsScreenContent(listGpsInformation)
}