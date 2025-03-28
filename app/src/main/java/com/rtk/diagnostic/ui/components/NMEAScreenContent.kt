package com.rtk.diagnostic.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun NMEAScreenContent(
    strNmeaData: String,
    onControlClick: () -> Unit,
    onLogClick: () -> Unit
)
{
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp) // 為按鈕預留空間
        ){
            if (strNmeaData.isNotEmpty())
            {
                Text(
                    text = strNmeaData,
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(start = 5.dp)
                )
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DiagnosticButton(
                text = "Start",
                onClick = onControlClick
            )
            DiagnosticButton(
                text = "Log on",
                onClick = onLogClick
            )
        }
    }
}
@Preview(showBackground = true, widthDp = 850, heightDp = 530)
@Composable
fun NMEAScreenPreview() {
    MaterialTheme {
        Surface(color = Color.Black) {
            NMEAScreenContent(
                strNmeaData = """
                    ${'$'}GPGGA,123519,4807.038,N,01131.000,E,1,08,0.9,545.4,M,46.9,M,,*47
                    ${'$'}GPRMC,123519,A,4807.038,N,01131.000,E,022.4,084.4,230394,003.1,W*6A                   
                    ${'$'}GPGSA,A,3,04,05,,09,12,,,24,,,,,2.5,1.3,2.1*39
                    ${'$'}GPGSV,2,1,08,01,40,083,46,02,17,308,41,12,07,344,39,14,22,228,45*75
                """.trimIndent(),
                onControlClick = {},
                onLogClick = {}
            )
        }
    }
}