package com.stc.openweatherapp.model

data class WeatherCondition(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)