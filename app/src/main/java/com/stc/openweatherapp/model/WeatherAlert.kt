package com.stc.openweatherapp.model

data class WeatherAlert(
    val sender_name: String,
    val event: String,
    val start: Long,
    val end: Long,
    val description: String,
    val tags: List<String>
)