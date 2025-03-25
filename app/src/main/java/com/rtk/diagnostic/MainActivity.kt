package com.rtk.diagnostic

import androidx.compose.ui.graphics.Color
import android.os.Bundle
import android.os.Trace
import androidx.compose.material3.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.rtk.diagnostic.ui.screen.MainMenu
import com.rtk.diagnostic.ui.screen.ToneScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rtk.diagnostic.ui.screen.TouchPanelScreen

class MainActivity : ComponentActivity() {
    private val selectedScreen = mutableStateOf<String?>(null)
    private lateinit var windowInsetsController: WindowInsetsControllerCompat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        setContent {
            MaterialTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(
                    color = Color.Black,
                    darkIcons = false
                )
                val currentScreen by remember { selectedScreen }
                when (currentScreen) {
                    "Tone display" -> ToneScreen(
                        onBackPressed = {
                            Trace.beginSection("ToneScreen_to_MainMenu_transition")
                            windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
                            selectedScreen.value = null
                            Trace.endSection()
                        }
                    )
                    "Touch panel confirm" -> TouchPanelScreen(
                        onBackPressed = {
                            Trace.beginSection("SensorScreen_to_MainMenu_transition")
                            windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
                            selectedScreen.value = null
                            Trace.endSection()
                        }
                    )
                    else -> MainMenu(
                        onFullScreenSelected = { screen ->
                            Trace.beginSection("MainMenu_to_${screen}_transition")
                            windowInsetsController.apply {
                                hide(WindowInsetsCompat.Type.systemBars())
                                systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                            }
                            selectedScreen.value = screen
                            Trace.endSection()
                        }
                    )
                }
            }
        }
    }
}