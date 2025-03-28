package com.rtk.diagnostic.data

data class NavigationItem(
    val strTitle: String
)

data class GpsInformation(
    val strData: String ="",
    val strTime: String ="",
    val strLatitude: String ="",
    val strLongitude: String ="",
    val strAlt: String ="",
    val strSpeed: String ="",
    val strSatellitesInFix: String ="",
    val strPositioningState: String ="",
    val listSatellites: List<SatelliteInfo> = emptyList()
)

data class SatelliteInfo(
    val iId: Int = 0,
    val iCNValue: Int = 0,
    val bIsUsed: Boolean = false
)