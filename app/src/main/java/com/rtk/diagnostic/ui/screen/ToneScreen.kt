package com.rtk.diagnostic.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rtk.diagnostic.ui.screen.ColorTestMode.*

enum class ColorTestMode
{
    COLOR_BARS,
    GRAYSCALE_BARS,
    FULL_WHITE,
    FULL_BLACK,
    BLACK_WITH_RED_BORDER
}
/**
 * Screen for testing display colors and tone quality
 * Cycles through different test patterns on click
 * @param onBackPressed Callback for when the user completes all test modes
 */
@Composable
fun ToneScreen(onBackPressed: () -> Unit = {}) {
    var currentMode by remember { mutableStateOf(ColorTestMode.COLOR_BARS) }
    val handleClick = {
        when (currentMode) {
            COLOR_BARS -> currentMode = GRAYSCALE_BARS
            GRAYSCALE_BARS -> currentMode = FULL_WHITE
            FULL_WHITE -> currentMode = FULL_BLACK
            FULL_BLACK -> currentMode = BLACK_WITH_RED_BORDER
            BLACK_WITH_RED_BORDER -> onBackPressed()
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            currentMode = ColorTestMode.COLOR_BARS
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = handleClick)
    ) {
        when (currentMode) {
            COLOR_BARS -> ColorBarsPattern()
            GRAYSCALE_BARS -> GrayscaleBarsPattern()
            FULL_WHITE -> FullColorPattern(Color.White)
            FULL_BLACK -> FullColorPattern(Color.Black)
            BLACK_WITH_RED_BORDER -> BlackWithRedBorderPattern()
        }
    }
}
/**
 * Black screen pattern with red border
 * Used to test screen edge display
 */
@Composable
fun BlackWithRedBorderPattern() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .border(BorderStroke(6.dp, Color.Red))
    )
}
/**
 * Color bars test pattern
 * Displays 8 color bars (black, blue, green, cyan, red, magenta, yellow, white)
 */
@Composable
fun ColorBarsPattern() {
    Row(modifier = Modifier.fillMaxSize())
    {
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxSize()
            .background(Color.Black))
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxSize()
            .background(Color.Blue))
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxSize()
            .background(Color.Green))
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxSize()
            .background(Color.Cyan))
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxSize()
            .background(Color.Red))
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxSize()
            .background(Color.Magenta))
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxSize()
            .background(Color.Yellow))
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxSize()
            .background(Color.White))
    }
}
/**
 * Grayscale bars test pattern
 * Displays 10 grayscale bars from black to white
 */
@Composable
fun GrayscaleBarsPattern() {
    Row(modifier = Modifier.fillMaxSize())
    {
        for (i in 0..9) {
            val grayValue = i / 9f
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .background(Color(grayValue, grayValue, grayValue))
            )
        }
    }
}
/**
 * Full color test pattern
 * Fills the entire screen with a single color
 * @param color The color to display full screen
 */

@Composable
fun FullColorPattern(color: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
    )
}
@Preview(showBackground = true, name = "Color bar Screen",widthDp = 850, heightDp = 530)
@Composable
fun ColorBarsPatternPreview() {
    MaterialTheme {
        ColorBarsPattern()
    }
}

@Preview(showBackground = true, name = "Gray scale Screen", widthDp = 850, heightDp = 530)
@Composable
fun GrayscaleBarsPatternPreview() {
    MaterialTheme {
        GrayscaleBarsPattern()
    }
}

@Preview(showBackground = true, name = "White Screen",widthDp = 850, heightDp = 530)
@Composable
fun FullWhitePatternPreview() {
    MaterialTheme {
        FullColorPattern(Color.White)
    }
}

@Preview(showBackground = true, name = "Black Screen", widthDp = 850, heightDp = 530)
@Composable
fun FullBlackPatternPreview() {
    MaterialTheme {
        FullColorPattern(Color.Black)
    }
}

@Preview(showBackground = true, name = "Black With RedBorder Screen", widthDp = 850, heightDp = 530)
@Composable
fun BlackWithRedBorderPatternPreview() {
    MaterialTheme {
        BlackWithRedBorderPattern()
    }
}