package com.rtk.diagnostic.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rtk.diagnostic.data.GpsInformation
import com.rtk.diagnostic.data.SatelliteInfo
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.layout.PaddingValues

@Composable
fun GpsScreenContent(
    gpsInformation: GpsInformation
)
{
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        GPSInfoDoubleRow("Date",gpsInformation.strData , "Time", gpsInformation.strTime)
        GPSInfoDoubleRow("Lat", gpsInformation.strLatitude, "Lon", gpsInformation.strLongitude)
        GPSInfoDoubleRow("Alt", gpsInformation.strAlt, "Speed", gpsInformation.strSpeed)
        GPSInfoDoubleRow("Satellites Infix", gpsInformation.strSatellitesInFix, "Positioning state", gpsInformation.strPositioningState)
        SatellitesGrid(satellites = gpsInformation.listSatellites)
    }
}

@Composable
fun GPSInfoDoubleRow(strLabel1: String, strValue1: String, strLabel2: String, strValue2: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            "$strLabel1: $strValue1",
            color = Color.White,
            fontSize = 15.sp,
            modifier = Modifier.weight(1f))
        Text(
            "$strLabel2: $strValue2",
            color = Color.White,
            fontSize = 15.sp,
            modifier = Modifier.weight(1f))
    }
}
@Composable
fun SatellitesGrid(satellites: List<SatelliteInfo>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        itemsIndexed(satellites) { index, satellite ->
            SatelliteInfoItem(index = index + 1, satellite = satellite)
        }
    }
}


@Composable
fun SatelliteInfoItem(index: Int, satellite: SatelliteInfo) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "satellite $index  ",
            color = Color.White,
            fontSize = 15.sp
        )
        Text(
            text = "No: ${satellite.iId}, ",
            color = Color.White,
            fontSize = 15.sp
        )
        Text(
            text = "C/N: ${satellite.iCNValue}, ",
            color = Color.White,
            fontSize = 15.sp
        )
        Text(
            text = "Used: ${satellite.bIsUsed}",
            color = Color.White,
            fontSize = 15.sp
        )
    }
}

@Preview(showBackground = true, widthDp = 850, heightDp = 530)
@Composable
fun GpsScreenPreview() {
    MaterialTheme {
        Surface(color = Color.Black) {
            GpsScreenContent(
                GpsInformation(
                    strData="2025/01/09", strTime = "15:41:00", strLatitude = "20.05729N", strLongitude = "121.366E",
                    listSatellites = listOf(
                        SatelliteInfo(1, 47, false),
                        SatelliteInfo(2, 48, true),
                        SatelliteInfo(4, 39, true),
                        SatelliteInfo(7, 49, true),
                        SatelliteInfo(8, 48, true),
                        SatelliteInfo(9, 42, true),
                        SatelliteInfo(10, 38, false),
                        SatelliteInfo(16, 39, true),
                        SatelliteInfo(21, 46, true),
                        SatelliteInfo(27, 42, true),
                        SatelliteInfo(30, 43, true),
                        SatelliteInfo(14, 0, false),
                        SatelliteInfo(17, 0, false),
                        SatelliteInfo(26, 0, false),
                        SatelliteInfo(38, 34, false),
                        SatelliteInfo(40, 40, false),
                        SatelliteInfo(41, 43, false),
                        SatelliteInfo(42, 34, false),
                        SatelliteInfo(50, 45, false)
                    ))
            )
        }
    }
}