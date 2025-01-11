package com.stc.openweatherapp.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.stc.openweatherapp.model.CurrentWeather
import com.stc.openweatherapp.util.capitalizeFirstLetter
import com.stc.openweatherapp.util.limitToOneDecimal

@Composable
fun CurrentWeatherScreen(currentWeather: CurrentWeather) {
    val iconUrl = currentWeather.weather.firstOrNull()?.icon?.let { iconId ->
        "https://openweathermap.org/img/wn/$iconId@4x.png"
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Now",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp)
        ) {


            if (iconUrl != null) {
                AsyncImage(
                    model = iconUrl,
                    contentDescription = "Weather Icon",
                    modifier = Modifier
                        .size(100.dp) // Set size for the icon
                        .padding(bottom = 16.dp)
                )
            }
            Text(
                text = "${currentWeather.temp.limitToOneDecimal()}°C",
                style = MaterialTheme.typography.displayMedium, // Larger font for temperature
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        Text(
            text = currentWeather.weather[0].description.capitalizeFirstLetter(),
            style = MaterialTheme.typography.titleMedium, // Slightly smaller font
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Wind speed
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = "Feels like: ${currentWeather.feels_like.limitToOneDecimal()}°C",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}