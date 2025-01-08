package com.stc.openweatherapp.model

data class CityInfo(
    val name: String,
    val local_names: Map<String, String>?, // Handle various local names as a map
    val lat: Double,
    val lon: Double,
    val country: String
)