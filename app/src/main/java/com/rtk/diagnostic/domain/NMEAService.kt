package com.rtk.diagnostic.domain

import android.content.Context
import android.location.LocationManager
import android.location.OnNmeaMessageListener
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NMEAService(private val context: Context)
{
    private val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private val _strNmeaData = MutableStateFlow<String>("")
    val strNmeaData: StateFlow<String> = _strNmeaData
    private val handler = Handler(Looper.getMainLooper())
    private val gnssNmeaListener = GnssNmeaListener()
    private var bIsListening = false
    fun startListening()
    {
        if (bIsListening) return

        try
        {
            locationManager.addNmeaListener(gnssNmeaListener, handler)
            bIsListening = true
        } catch (e: SecurityException)
        {
            _strNmeaData.value=("Error: Location permission not granted")
        }
    }
    fun stopListening() {
        if (!bIsListening) return
        locationManager.removeNmeaListener(gnssNmeaListener)
        bIsListening = false
    }
    private inner class GnssNmeaListener : OnNmeaMessageListener {
        override fun onNmeaMessage(message: String?, timestamp: Long) {
            message?.let {
                _strNmeaData.value= it
            }
        }
    }
}