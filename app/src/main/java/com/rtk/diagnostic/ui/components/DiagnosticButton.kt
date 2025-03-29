package com.rtk.diagnostic.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Standard button used in the diagnostic app
 * @param strText Text to display in the button
 * @param onClick Function to execute when button is clicked
 * @param modifier Modifier for customizing the button appearance
 */
@Composable
fun DiagnosticButton(
    strText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.LightGray
        ),
        shape = RectangleShape,
        modifier = modifier
            .width(100.dp)
            .height(45.dp)
    ) {
        Text(strText, color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun DiagnosticButtonPreview() {
    MaterialTheme {
        DiagnosticButton(
            strText = "TEST",
            onClick = { }
        )
    }
}

