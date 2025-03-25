package com.rtk.diagnostic.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.RectangleShape
import com.rtk.diagnostic.ui.components.DiagnosticButton

@Composable
fun NMEAScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "Test",
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopStart)
            .padding(16.dp))
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
            DiagnosticButton(
                text ="",
                onClick={}
            )
            DiagnosticButton(
                text ="",
                onClick={}
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 850, heightDp = 530)
@Composable
fun NMEAScreenPreview()
{
    MaterialTheme {
        Surface(color = Color.Black) {
            NMEAScreen()
        }
    }
}