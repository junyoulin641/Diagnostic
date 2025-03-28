package com.rtk.diagnostic.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rtk.diagnostic.domain.NMEAService
import kotlinx.coroutines.flow.StateFlow

class NMEAViewModel(application: Application) : AndroidViewModel(application) {

    private val nmeaService = NMEAService(application.applicationContext)
    val strNmeaData: StateFlow<String> = nmeaService.strNmeaData
    fun startNmeaListening() {
        nmeaService.startListening()
    }
    fun stopNmeaListening() {
        nmeaService.stopListening()
    }

    fun parseNmeaData(nmeaString: String): Map<String, String> {
        val result = mutableMapOf<String, String>()


        return result
    }

    // ViewModel销毁时清理资源
    override fun onCleared() {
        stopNmeaListening()
        super.onCleared()
    }
}