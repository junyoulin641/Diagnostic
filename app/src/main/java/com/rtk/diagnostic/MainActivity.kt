package com.rtk.diagnostic

import android.content.Intent
import androidx.compose.ui.graphics.Color
import android.os.Bundle
import android.os.Trace
import androidx.compose.material3.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rtk.diagnostic.ui.screen.MainMenu
import com.rtk.diagnostic.ui.screen.ToneScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rtk.diagnostic.ui.components.DiagnosticButton
import com.rtk.diagnostic.ui.screen.TouchPanelScreen
import com.rtk.diagnostic.utils.LocationPermissionHandler
import com.rtk.diagnostic.utils.RequestLocationPermission
import com.rtk.diagnostic.viewmodel.GpsViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: GpsViewModel by viewModels()
    private val selectedScreen = mutableStateOf<String?>(null)
    private lateinit var windowInsetsController: WindowInsetsControllerCompat

    private val bLocationPermission = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)

        // Check permission when the app starts
        val locationHandler = LocationPermissionHandler(this)
        bLocationPermission.value = locationHandler.hasLocationPermission()
        setContent {
            MaterialTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(
                    color = Color.Black,
                    darkIcons = false
                )

                val lifecycleOwner = LocalLifecycleOwner.current
                DisposableEffect(lifecycleOwner) {
                    if (bLocationPermission.value)
                    {
                        viewModel.startNmeaListening()
                    }
                    onDispose {
                        viewModel.stopNmeaListening()
                    }
                }

                RequestLocationPermission(
                    onPermissionGranted = {
                        bLocationPermission.value = true
                        viewModel.startNmeaListening()
                    },
                    onPermissionDenied = {
                        bLocationPermission.value = false
                    }
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
                                systemBarsBehavior =
                                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
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