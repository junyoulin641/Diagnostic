package com.rtk.diagnostic.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun DiagnosticButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
)
{
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.LightGray
        ),
        shape = RectangleShape,
        modifier = Modifier
            .width(100.dp)
            .height(45.dp)
    ) {
        Text(text, color = Color.Black)
    }
}