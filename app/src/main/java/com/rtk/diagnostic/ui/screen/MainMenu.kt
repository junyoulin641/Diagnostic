package com.rtk.diagnostic.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rtk.diagnostic.data.NavigationItem

@Composable
fun MainMenu() {
    val navigationItems = listOf(
        NavigationItem("Version information"),
        NavigationItem("GPS information"),
        NavigationItem("NMEA information"),
        NavigationItem("Tone display"),
        NavigationItem("Sensor value"),
        NavigationItem("Touch panel confirm")
    )

    var selectedItem by remember { mutableStateOf(navigationItems[0]) }

    Row(modifier = Modifier.fillMaxSize())
    {
        NavigationSidebar(
            items = navigationItems,
            onItemSelected = { item -> selectedItem = item },
            modifier = Modifier
                .width(200.dp)
                .fillMaxHeight()
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(16.dp)
        ) {
            // 根据选中的导航项显示不同内容
            ContentScreen(selectedItem)
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
