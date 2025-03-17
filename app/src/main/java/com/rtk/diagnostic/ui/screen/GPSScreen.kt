import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GPSScreen(hasSignal: Boolean = true) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (hasSignal) {
            // 显示GPS信息
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // 使用LazyColumn可能更适合大量数据，但为了保持简单使用Column
                GPSInfoItem("Date : 2025/01/09")
                GPSInfoItem("Time : 15:41:00")
                GPSInfoItem("Lat : 25.05729N")
                GPSInfoItem("Lon : 121.36629E")
                GPSInfoItem("Alt : 288.7M")
                GPSInfoItem("Speed : 0.0km/h")
                GPSInfoItem("Satellites InFix : 9")
                GPSInfoItem("positioning state : Available")
                GPSInfoItem("satellite 1  No: 1,C/N: 47, Used: false")
                GPSInfoItem("satellite 2  No: 2,C/N: 48, Used: true")
                GPSInfoItem("satellite 3  No: 4,C/N: 39, Used: true")
                GPSInfoItem("satellite 4  No: 7,C/N: 49, Used: true")
                GPSInfoItem("satellite 5  No: 8,C/N: 48, Used: true")
                GPSInfoItem("satellite 6  No: 9,C/N: 42, Used: true")
                GPSInfoItem("satellite 7  No: 10,C/N: 38, Used: false")
                GPSInfoItem("satellite 8  No: 16,C/N: 39, Used: true")
                GPSInfoItem("satellite 9  No: 21,C/N: 46, Used: true")
                GPSInfoItem("satellite 10  No: 27,C/N: 42, Used: true")
                GPSInfoItem("satellite 11  No: 30,C/N: 43, Used: true")
                GPSInfoItem("satellite 12  No: 14,C/N: 0, Used: false")
                GPSInfoItem("satellite 13  No: 17,C/N: 0, Used: false")
                GPSInfoItem("satellite 14  No: 26,C/N: 0, Used: false")
                GPSInfoItem("satellite 15  No: 38,C/N: 34, Used: false")
                GPSInfoItem("satellite 16  No: 40,C/N: 40, Used: false")
                GPSInfoItem("satellite 17  No: 41,C/N: 43, Used: false")
                GPSInfoItem("satellite 18  No: 42,C/N: 34, Used: false")
                GPSInfoItem("satellite 19  No: 50,C/N: 45, Used: false")
            }
        } else {
            // 显示"No Signal"
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No Signal",
                    color = Color.White,
                    fontSize = 40.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun GPSInfoItem(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
    }
}

// 更高级的实现使用两列布局
@Composable
fun GPSScreenWithTwoColumns() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            repeat(14) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // 左列
                    val leftIndex = row * 2 + 1
                    if (leftIndex <= 28) {
                        GPSInfoItemInGrid(getTextForIndex(leftIndex), Modifier.weight(1f))
                    }

                    // 右列
                    val rightIndex = row * 2 + 2
                    if (rightIndex <= 28) {
                        GPSInfoItemInGrid(getTextForIndex(rightIndex), Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun GPSInfoItemInGrid(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

fun getTextForIndex(index: Int): String {
    return when (index) {
        1 -> "Date : 2025/01/09"
        2 -> "Time : 15:41:00"
        3 -> "Lat : 25.05729N"
        4 -> "Lon : 121.36629E"
        5 -> "Alt : 288.7M"
        6 -> "Speed : 0.0km/h"
        7 -> "Satellites InFix : 9"
        8 -> "positioning state : Available"
        9 -> "satellite 1  No: 1,C/N: 47, Used: false"
        10 -> "satellite 2  No: 2,C/N: 48, Used: true"
        11 -> "satellite 3  No: 4,C/N: 39, Used: true"
        12 -> "satellite 4  No: 7,C/N: 49, Used: true"
        13 -> "satellite 5  No: 8,C/N: 48, Used: true"
        14 -> "satellite 6  No: 9,C/N: 42, Used: true"
        15 -> "satellite 7  No: 10,C/N: 38, Used: false"
        16 -> "satellite 8  No: 16,C/N: 39, Used: true"
        17 -> "satellite 9  No: 21,C/N: 46, Used: true"
        18 -> "satellite 10  No: 27,C/N: 42, Used: true"
        19 -> "satellite 11  No: 30,C/N: 43, Used: true"
        20 -> "satellite 12  No: 14,C/N: 0, Used: false"
        21 -> "satellite 13  No: 17,C/N: 0, Used: false"
        22 -> "satellite 14  No: 26,C/N: 0, Used: false"
        23 -> "satellite 15  No: 38,C/N: 34, Used: false"
        24 -> "satellite 16  No: 40,C/N: 40, Used: false"
        25 -> "satellite 17  No: 41,C/N: 43, Used: false"
        26 -> "satellite 18  No: 42,C/N: 34, Used: false"
        27 -> "satellite 19  No: 50,C/N: 45, Used: false"
        else -> ""
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun GPSScreenPreview() {
    MaterialTheme {
        Surface(color = Color.Black) {
            GPSScreen(hasSignal = true)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun GPSScreenNoSignalPreview() {
    MaterialTheme {
        Surface(color = Color.Black) {
            GPSScreen(hasSignal = false)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun GPSScreenWithTwoColumnsPreview() {
    MaterialTheme {
        Surface(color = Color.Black) {
            GPSScreenWithTwoColumns()
        }
    }
}