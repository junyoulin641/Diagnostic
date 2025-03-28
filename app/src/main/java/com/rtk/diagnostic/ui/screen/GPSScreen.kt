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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rtk.diagnostic.ui.components.GpsScreenContent
import com.rtk.diagnostic.utils.LocationPermissionHandler
import com.rtk.diagnostic.utils.RequestLocationPermission
import com.rtk.diagnostic.viewmodel.GpsViewModel

@Composable
fun GPSScreen(hasSignal: Boolean = true) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val locationPermissionHandler = remember { LocationPermissionHandler(context) }
    val viewModel: GpsViewModel = viewModel()
    val strNmeaData by viewModel.strNmeaData.collectAsState("")
    val listGpsInformation by viewModel.listGpsInformation.collectAsState()
    var displayText by remember { mutableStateOf(strNmeaData) }
    var bLocationPermission by remember { mutableStateOf(locationPermissionHandler.hasLocationPermission()) }
    var bGpsEnabled by remember { mutableStateOf(locationPermissionHandler.isGpsEnabled()) }
    DisposableEffect(lifecycleOwner, bLocationPermission, bGpsEnabled) {
        if (bLocationPermission && bGpsEnabled)
        {
            viewModel.startNmeaListening()
        }
        onDispose {
        }
    }
    when {
        !bLocationPermission -> {
            RequestLocationPermission(
                onPermissionGranted = {
                    bLocationPermission = true
                    bGpsEnabled = locationPermissionHandler.isGpsEnabled()
                    if (bGpsEnabled)
                    {
                        viewModel.startNmeaListening()
                    }
                },
                onPermissionDenied = {
                    bLocationPermission = false
                }
            )
            displayText="Location permission is required for NMEA data"
        }
        !bGpsEnabled ->{
            displayText="Please enable location services in system settings"
        }
        else -> {
            displayText = strNmeaData
        }
    }
    GpsScreenContent(listGpsInformation)
}