package com.rtk.diagnostic.domain

import android.content.Context
import android.location.Location
import android.location.GnssStatus
import android.location.LocationListener
import android.location.LocationManager
import android.location.OnNmeaMessageListener
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.rtk.diagnostic.data.SatelliteInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GPSService(private val context: Context)
{
    private val TAG = "GPSService"
    private val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private val _strNmeaData = MutableStateFlow<String>("")
    val strNmeaData: StateFlow<String> = _strNmeaData
    private val handler = Handler(Looper.getMainLooper())
    private val gnssNmeaListener = GnssNmeaListener()
    private var bIsListening = false
    private val satellites = mutableListOf<SatelliteInfo>()
    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location)
        {
            Log.d(TAG, "Location update: ${location.latitude}, ${location.longitude}")
        }
        override fun onProviderEnabled(provider: String)
        {
            Log.d(TAG, "Provider enabled: $provider")
        }
        override fun onProviderDisabled(provider: String) {
            Log.d(TAG, "Provider disabled: $provider")
        }
    }
    private val gnssStatusCallback = object : GnssStatus.Callback() {
        override fun onSatelliteStatusChanged(status: GnssStatus) {
            Log.d(TAG, "Satellite status updated: ${status.satelliteCount} satellites")

            satellites.clear()
            for (i in 0 until status.satelliteCount) {
                val id = status.getSvid(i)
                val signalStrength = (status.getCn0DbHz(i)).toInt()
                val isUsed = status.usedInFix(i)

                satellites.add(SatelliteInfo(id, signalStrength, isUsed))
            }
        }
    }
    fun startListening()
    {
        if (bIsListening) return

        try
        {
            locationManager.addNmeaListener(gnssNmeaListener, handler)
            locationManager.registerGnssStatusCallback(gnssStatusCallback, handler)
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000,
                0f,
                locationListener,
                Looper.getMainLooper()
            )
            bIsListening = true
        } catch (e: SecurityException)
        {
            _strNmeaData.value=("Error: Location permission not granted")
        }
    }
    fun stopListening()
    {
        if (!bIsListening) return
        locationManager.removeNmeaListener(gnssNmeaListener)
        locationManager.removeUpdates(locationListener)
        bIsListening = false
    }
    private inner class GnssNmeaListener : OnNmeaMessageListener {
        override fun onNmeaMessage(strNmea: String?, timestamp: Long) {
            strNmea?.let {
                _strNmeaData.value = it
            }
        }
    }
}