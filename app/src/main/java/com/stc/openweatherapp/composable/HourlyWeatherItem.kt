package com.stc.openweatherapp.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.stc.openweatherapp.model.HourlyWeather
import com.stc.openweatherapp.util.round
import java.util.Date

@Composable
fun HourlyWeatherItem(hourlyWeather: HourlyWeather) {
    val context = LocalContext.current

    val hour = remember(hourlyWeather.dt) {
        val timeFormat = android.text.format.DateFormat.getTimeFormat(context)
        timeFormat.format(Date(hourlyWeather.dt * 1000)) // Convert Unix time to milliseconds
    }
    val iconUrl = hourlyWeather.weather.firstOrNull()?.icon?.let { iconId ->
        "https://openweathermap.org/img/wn/$iconId@2x.png"
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(60.dp) // Set a fixed width for uniformity
    ) {
        // Weather Icon
        if (iconUrl != null) {
            AsyncImage(
                model = iconUrl,
                contentDescription = "Weather Icon",
                modifier = Modifier.size(48.dp)
            )
        }

        // Temperature
        Text(
            text = "${hourlyWeather.temp.round()}°C",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp)
        )

        // Probability of Rain/Snow
        Text(
            text = "${(hourlyWeather.pop * 100).toInt()}%",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 2.dp),
            color = MaterialTheme.colorScheme.primary
        )

        // Hour
        Text(
            text = hour,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}