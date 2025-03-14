package com.rtk.diagnostic.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.rtk.diagnostic.data.NavigationItem
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
@Composable
fun ContentScreen(navigationItem: NavigationItem) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (navigationItem.title) {
//            "Version information" -> HomeScreen()
//            "GPS information" -> ProfileScreen()
//            "NMEA information" -> ShoppingScreen()
//            "Tone display" -> SettingsScreen()
//            "Sensor value" -> SettingsScreen()
//            "Touch panel confirm" -> SettingsScreen()
        }
    }
}
