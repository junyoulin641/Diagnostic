package com.rtk.diagnostic.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rtk.diagnostic.ui.components.DiagnosticButton

@Composable
fun TouchPanelScreen(onBackPressed: () -> Unit = {}) {
    var touchPosition by remember { mutableStateOf(Offset.Zero) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTapGestures { tapOffset ->
                    touchPosition = tapOffset
                }
            }
    ) {
        DiagnosticButton(
            text ="Back",
            modifier = Modifier
                .align(Alignment.TopEnd),
            onClick=onBackPressed
        )
        Text(
            text = "Touch panel confimation (${touchPosition.x.toInt()},${touchPosition.y.toInt()})",
            color = Color.White,
            fontSize = 15.sp,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        )
        CircleSquareIcon(
            modifier = Modifier.align(Alignment.Center)
        )

        CircleSquareIcon(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = 40.dp)
        )
        CircleSquareIcon(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 40.dp)
        )
        CircleSquareIcon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(x = (-40).dp)
        )
        CircleSquareIcon(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-40).dp)
        )
    }
}

@Composable
fun CircleSquareIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val size = 60f
        drawCircle(
            color = Color.White,
            radius = size / 2,
            style = Stroke(width = 2f)
        )
        val squareSize = size / 3
        val topLeft = Offset(
            x = -squareSize / 2,
            y = -squareSize / 2
        )
        drawRect(
            color = Color.White,
            topLeft = topLeft,
            size = Size(squareSize, squareSize),
            style = Stroke(width = 2f)
        )
    }
}

@Preview(showBackground = true, widthDp = 850, heightDp = 530)
@Composable
fun TouchPanelScreenPreview()
{
    MaterialTheme {
        Surface(color = Color.Black) {
            TouchPanelScreen()
        }
    }
}