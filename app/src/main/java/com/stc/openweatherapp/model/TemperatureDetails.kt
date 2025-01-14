package com.stc.openweatherapp.model

data class TemperatureDetails(
    val min: Double,
    val max: Double,
    val afternoon: Double,
    val night: Double,
    val evening: Double,
    val morning: Double
)