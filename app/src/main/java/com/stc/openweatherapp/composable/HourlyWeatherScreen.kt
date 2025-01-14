package com.stc.openweatherapp.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stc.openweatherapp.model.HourlyWeather

@Composable
fun HourlyWeatherScreen(hourlyWeatherList: List<HourlyWeather>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        items(hourlyWeatherList) { hourlyWeather ->
            HourlyWeatherItem(hourlyWeather = hourlyWeather)
        }
    }
}