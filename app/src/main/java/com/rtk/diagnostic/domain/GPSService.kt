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

/**
 * Service responsible for managing GPS/GNSS functionality
 * @param context Application context needed for accessing system services
 */
class GPSService(private val context: Context) {
    private val TAG = "GpsService"
    private val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private val _strNmeaData = MutableStateFlow<String>("")
    val strNmeaData: StateFlow<String> = _strNmeaData
    private val handler = Handler(Looper.getMainLooper())
    private val gnssNmeaListener = GnssNmeaListener()
    private var bIsListening = false
    private val satellites = mutableListOf<SatelliteInfo>()

    /**
     * Location listener implementation for receiving GPS location updates
     */
    private val locationListener = object : LocationListener {
        /**
         * Called when device location changes
         * @param location The new location
         */
        override fun onLocationChanged(location: Location) {
            Log.d(TAG, "Location update: ${location.latitude}, ${location.longitude}")
        }

        /**
         * Called when a location provider is enabled
         * @param strProvider The name of the provider
         */
        override fun onProviderEnabled(strProvider: String) {
            Log.d(TAG, "Provider enabled: $strProvider")
        }

        /**
         * Called when a location provider is disabled
         * @param strProvider The name of the provider
         */
        override fun onProviderDisabled(strProvider: String) {
            Log.d(TAG, "Provider disabled: $strProvider")
        }
    }

    /**
     * GNSS status callback for receiving satellite information
     */
    private val gnssStatusCallback = object : GnssStatus.Callback() {
        /**
         * Called when satellite status changes
         * @param status The updated GNSS status
         */
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

    /**
     * Start listening for GPS/GNSS updates
     * Registers listeners for NMEA messages, GNSS status, and location updates
     */
    fun startListening() {
        if (bIsListening) return

        try {
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
        } catch (e: SecurityException) {
            _strNmeaData.value = "Error: Location permission not granted"
        }
    }

    /**
     * Stop listening for GPS/GNSS updates
     * Unregisters all listeners
     */
    fun stopListening() {
        if (!bIsListening) return
        locationManager.removeNmeaListener(gnssNmeaListener)
        locationManager.removeUpdates(locationListener)
        bIsListening = false
    }

    /**
     * Inner class that listens for NMEA messages from the GPS
     */
    private inner class GnssNmeaListener : OnNmeaMessageListener {
        /**
         * Called when a new NMEA message is received
         * @param strNmea The NMEA message
         * @param timestamp The timestamp when the message was received
         */
        override fun onNmeaMessage(strNmea: String?, timestamp: Long) {
            strNmea?.let {
                _strNmeaData.value = it
            }
        }
    }
}