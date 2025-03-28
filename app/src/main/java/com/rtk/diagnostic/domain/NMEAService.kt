package com.rtk.diagnostic.domain

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.OnNmeaMessageListener
import android.os.Handler
import android.os.Looper
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NMEAService(private val context: Context)
{
    private val TAG = "NMEAService"
    private val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private val _strNmeaData = MutableStateFlow<String>("")
    val strNmeaData: StateFlow<String> = _strNmeaData
    private val handler = Handler(Looper.getMainLooper())
    private val gnssNmeaListener = GnssNmeaListener()
    private var bIsListening = false
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
    fun startListening()
    {
        if (bIsListening) return

        try
        {
            locationManager.addNmeaListener(gnssNmeaListener, handler)
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000, // 1 秒更新一次
                0f,   // 無最小距離限制
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