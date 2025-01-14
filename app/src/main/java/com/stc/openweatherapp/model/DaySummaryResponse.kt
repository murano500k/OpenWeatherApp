package com.stc.openweatherapp.model

data class DaySummaryResponse(
    val lat: Double,
    val lon: Double,
    val tz: String,
    val date: String,
    val units: String,
    val cloud_cover: CloudCover,
    val humidity: Humidity,
    val precipitation: Precipitation,
    val temperature: TemperatureDetails,
    val pressure: PressureDetails,
    val wind: WindDetails
)