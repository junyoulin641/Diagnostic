package com.rtk.diagnostic.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rtk.diagnostic.data.GpsInformation
import com.rtk.diagnostic.domain.GPSService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GpsViewModel(application: Application) : AndroidViewModel(application)
{
    private val TAG = "NMEAViewModel"
    private val GPSService = GPSService(application.applicationContext)
    private val _strDisplayNmeaData = MutableStateFlow<String>("")
    val strNmeaData: StateFlow<String> = _strDisplayNmeaData
    private val _listGpsInformation = MutableStateFlow(GpsInformation())
    val listGpsInformation: StateFlow<GpsInformation> = _listGpsInformation.asStateFlow()
    private val strNmeaMessages = mutableListOf<String>()
    private val iMaxMessages = 18
    init {
        viewModelScope.launch {
            GPSService.strNmeaData.collect { strMessage ->
                if (strMessage.isNotEmpty())
                {
                    addNmeaMessage(strMessage)
                    val parsedData = parseNmeaData(strMessage)
                    logParsedNmeaData(strMessage, parsedData)
                    updateDisplayText()
                }
            }
        }
    }
    fun startNmeaListening()
    {
        GPSService.startListening()
    }
    fun stopNmeaListening()
    {
        GPSService.stopListening()
    }

    fun clearNmeaData()
    {
        strNmeaMessages.clear()
        updateDisplayText()
    }

    private fun addNmeaMessage(strMessage: String)
    {
        strNmeaMessages.add(strMessage)
        if (strNmeaMessages.size > iMaxMessages)
        {
            strNmeaMessages.removeAt(0)
        }
    }

    private fun updateDisplayText() {
        _strDisplayNmeaData.value = strNmeaMessages.joinToString("")
    }
    fun parseNmeaData(nmeaString: String): Map<String, String>
    {
        val result = mutableMapOf<String, String>()

        try {
            val parts = nmeaString.split(",")
            if (parts.isNotEmpty()) {
                val messageType = parts[0].removePrefix("$")
                result["messageType"] = messageType
                when {
                    messageType.contains("GGA") -> {
                        if (parts.size >= 15)
                        {
                            result["time"] = parts[1]
                            result["latitude"] = "${parts[2]} ${parts[3]}"
                            result["longitude"] = "${parts[4]} ${parts[5]}"
                            result["fixQuality"] = parts[6]
                            result["satellites"] = parts[7]
                            result["hdop"] = parts[8]
                            result["altitude"] = "${parts[9]} ${parts[10]}"
                        }
                    }
                    messageType.contains("RMC") -> {
                        if (parts.size >= 12)
                        {
                            result["time"] = parts[1]
                            result["status"] = parts[2]
                            result["latitude"] = "${parts[3]} ${parts[4]}"
                            result["longitude"] = "${parts[5]} ${parts[6]}"
                            result["speed"] = parts[7]
                            result["trackAngle"] = parts[8]
                            result["date"] = parts[9]
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing NMEA data: ${e.message}")
            result["error"] = "Parse error: ${e.message}"
        }

        return result
    }

    private fun logParsedNmeaData(originalMessage: String, parsedData: Map<String, String>)
    {
        val messageType = parsedData["messageType"] ?: ""

        if (messageType.contains("GGA") || messageType.contains("RMC"))
        {
            Log.d(TAG, "Original NMEA: $originalMessage")
            Log.d(TAG, "Parsed NMEA data: ${parsedData.entries.joinToString(", ") { "${it.key}=${it.value}" }}")
        }
    }
    override fun onCleared()
    {
        stopNmeaListening()
        super.onCleared()
    }
}