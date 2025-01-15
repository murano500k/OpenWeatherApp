package com.stc.openweatherapp.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stc.openweatherapp.model.WeatherResponse

@Composable
fun WeatherCardGrid(weatherData: WeatherResponse) {
    val windSpeed = weatherData.current.wind_speed
    val windDeg = weatherData.current.wind_deg
    val humidity = weatherData.current.humidity
    val dewPoint = weatherData.current.dew_point
    val uvi = weatherData.current.uvi
    val pressure = weatherData.current.pressure

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Left Column
        Column(
            modifier = Modifier
                .weight(1f),  // ensures each column shares width equally
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 1) Box for WindCard
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f) // forces square
            ) {
                WindCard(windSpeed = windSpeed, windDeg = windDeg)
            }

            // 2) Box for HumidityCard
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                HumidityCard(
                    humidity = humidity,
                    dewPoint = dewPoint,
                    modifier = Modifier.Companion.clickable { })
            }
        }

        // Right Column
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 3) Box for UviCard
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                UviCard(uvi = uvi)
            }

            // 4) Box for PressureCard
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                PressureCard(pressure = pressure)
            }
        }
    }
}