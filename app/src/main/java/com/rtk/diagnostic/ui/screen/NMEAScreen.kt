package com.rtk.diagnostic.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rtk.diagnostic.ui.components.NMEAScreenContent
import com.rtk.diagnostic.utils.LocationPermissionHandler
import com.rtk.diagnostic.utils.RequestLocationPermission
import com.rtk.diagnostic.viewmodel.NMEAViewModel

@Composable
fun NMEAScreen()
{
    val viewModel: NMEAViewModel = viewModel()
    val strNmeaData by viewModel.strNmeaData.collectAsState("")
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val locationPermissionHandler = remember { LocationPermissionHandler(context) }
    var hasPermission by remember { mutableStateOf(locationPermissionHandler.hasLocationPermission()) }
    var isGpsEnabled by remember { mutableStateOf(locationPermissionHandler.isGpsEnabled()) }
    if (!isGpsEnabled && !hasPermission)
    {
        RequestLocationPermission(
            onPermissionGranted =
            {
                hasPermission = true
                viewModel.startNmeaListening()
            },
            onPermissionDenied =
            {
                hasPermission = false
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "You should allow app to permission your location.",
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
    else
    {
        DisposableEffect(lifecycleOwner) {
            viewModel.startNmeaListening()
            onDispose {
                viewModel.stopNmeaListening()
            }
        }
        NMEAScreenContent(
            strNmeaData = strNmeaData,
            onControlClick = { },
            onLogClick = { }
        )
    }
}
