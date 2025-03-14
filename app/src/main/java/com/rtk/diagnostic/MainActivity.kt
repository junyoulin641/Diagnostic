package com.rtk.diagnostic

import android.os.Bundle
import androidx.compose.material3.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.rtk.diagnostic.ui.screen.MainMenu

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainMenu()
            }
        }
    }
}