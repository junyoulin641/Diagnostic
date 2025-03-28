package com.rtk.diagnostic.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rtk.diagnostic.data.GpsInformation

@Composable
fun GpsScreenContent(
    GpsInformation: GpsInformation
)
{

}


@Preview(showBackground = true, widthDp = 850, heightDp = 530)
@Composable
fun GpsScreenPreview() {
    MaterialTheme {
        Surface(color = Color.Black) {
            GpsScreenContent(
                GpsInformation(strData="2025/01/09", strTime = "15:41:00", strLatitude = "20.05729N", strLongitude = "121.366E")
            )
        }
    }
}