package com.rtk.diagnostic.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun VersionScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            VersionRow(label = "   Version information", value = "")
            VersionRow(label = "OS Version", value = "0.1")
            VersionRow(label = "MCU Version", value = "0.1")
            VersionRow(label = "GPS Version", value = "0.1")
        }
    }

}
@Composable
fun VersionRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 15.sp,
            maxLines = 1,
            modifier = Modifier
                .weight(1f)
                .padding(10.dp)
        )
        Text(
            text = value,
            color = Color.White,
            fontSize = 15.sp,
            modifier = Modifier
                .weight(1f)
                .padding(10.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 400)
@Composable
fun VersionInfoScreenPreview() {
    MaterialTheme {
        VersionScreen()
    }
}
