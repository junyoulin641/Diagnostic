package com.rtk.diagnostic.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rtk.diagnostic.data.NavigationItem
import com.rtk.diagnostic.ui.components.SidebarButton


@Composable
fun NavigationSidebar(
    items: List<NavigationItem>,
    onItemSelected: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        items.forEach { item ->
            SidebarButton(
                strText = item.strTitle,
                onClick = {onItemSelected(item) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}
@Preview(showBackground = true, widthDp = 200, heightDp = 500)
@Composable
fun NavigationSidebarWithBackgroundPreview() {
    MaterialTheme {
        NavigationSidebar(
            items = listOf(
                NavigationItem("Version information"),
                NavigationItem("GPS information"),
                NavigationItem("NMEA information"),
                NavigationItem("Tone display"),
                NavigationItem("Sensor value"),
                NavigationItem("Touch panel confirm"),
            ),
            onItemSelected = {}
        )
    }
}