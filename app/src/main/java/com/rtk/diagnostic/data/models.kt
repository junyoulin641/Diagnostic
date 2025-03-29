package com.rtk.diagnostic.data

/**
 * Data class representing a navigation menu item
 * @param strTitle The title text of the navigation item
 */
data class NavigationItem(
    val strTitle: String
)

/**
 * Data class containing GPS information data for display
 * @param strData The date information
 * @param strTime The time information
 * @param strLatitude The latitude coordinate
 * @param strLongitude The longitude coordinate
 * @param strAlt The altitude value
 * @param strSpeed The speed value
 * @param strSatellitesInFix The number of satellites used in the GPS fix
 * @param strPositioningState The current positioning state
 * @param listSatellites List of satellite information
 */
data class GpsInformation(
    val strData: String = "",
    val strTime: String = "",
    val strLatitude: String = "",
    val strLongitude: String = "",
    val strAlt: String = "",
    val strSpeed: String = "",
    val strSatellitesInFix: String = "",
    val strPositioningState: String = "",
    val listSatellites: List<SatelliteInfo> = emptyList()
)

/**
 * Data class containing information about a single satellite
 * @param iId The satellite ID number
 * @param iCNValue The carrier-to-noise ratio (signal strength) in dB-Hz
 * @param bIsUsed Boolean indicating if this satellite is used in the current fix
 */
data class SatelliteInfo(
    val iId: Int = 0,
    val iCNValue: Int = 0,
    val bIsUsed: Boolean = false
)