package com.rtk.diagnostic.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rtk.diagnostic.data.NavigationItem

@Composable
fun MainMenu(onToneDisplaySelected: () -> Unit = {}) {
    val navigationItems = listOf(
        NavigationItem(strTitle = "Version information"),
        NavigationItem(strTitle = "GPS information"),
        NavigationItem(strTitle = "NMEA information"),
        NavigationItem(strTitle = "Tone display"),
        NavigationItem(strTitle = "Sensor value"),
        NavigationItem(strTitle = "Touch panel confirm")
    )
    var selectedItem by remember { mutableStateOf<NavigationItem?>(null) }
    val systemUiController = rememberSystemUiController()
    DisposableEffect(systemUiController)
    {
        systemUiController.setStatusBarColor(
            color = Color.Black,
            darkIcons = false
        )
        systemUiController.setNavigationBarColor(
            color = Color.Black,
            darkIcons = false
        )
        onDispose { }
    }
    Row(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black))
    {
        NavigationSidebar(
            items = navigationItems,
            onItemSelected = { item ->
                if (item.strTitle == "Tone display")
                {
                    onToneDisplaySelected()
                }
                else
                {
                    selectedItem = item
                }
            },
            modifier = Modifier
                .width(250.dp)
                .fillMaxHeight()
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            selectedItem?.let { ContentScreen(it) }
        }
    }
}

@Preview(showBackground = true, widthDp = 850, heightDp = 530)
@Composable
fun MainMenuPreview() {
    MaterialTheme {
        MainMenu()
    }
}
