package com.rtk.diagnostic

import androidx.compose.ui.graphics.Color
import android.os.Bundle
import android.os.Trace
import androidx.compose.material3.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.rtk.diagnostic.ui.screen.MainMenu
import com.rtk.diagnostic.ui.screen.ToneScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rtk.diagnostic.ui.screen.TouchPanelScreen
import com.rtk.diagnostic.utils.LocationPermissionHandler
import com.rtk.diagnostic.utils.RequestLocationPermission
import com.rtk.diagnostic.viewmodel.GpsViewModel

/**
 * Main Activity for the Diagnostic application
 * Handles screen navigation and permission management
 */
class MainActivity : ComponentActivity() {
    private val viewModel: GpsViewModel by viewModels()
    private val selectedScreen = mutableStateOf<String?>(null)
    private lateinit var windowInsetsController: WindowInsetsControllerCompat

    private val bLocationPermission = mutableStateOf(false)

    /**
     * Initialize the activity and set up the UI
     * @param savedInstanceState If non-null, this activity is being re-constructed from a previous saved state
     */
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
                    if (bLocationPermission.value) {
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