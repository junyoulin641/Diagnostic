package com.rtk.diagnostic.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import com.rtk.diagnostic.data.NavigationItem
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.rtk.diagnostic.ui.screen.GPSScreen
@Composable
fun ContentScreen(navigationItem: NavigationItem) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.Black
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (navigationItem.strTitle) {
            "Version information" -> VersionScreen()
            "GPS information" -> GPSScreen()
            "NMEA information" -> NMEAScreen()
            "Tone display" -> ToneScreen()
            "Sensor value" -> SensorScreen()
            "Touch panel confirm" -> TouchPanelScreen()
        }
    }
}
