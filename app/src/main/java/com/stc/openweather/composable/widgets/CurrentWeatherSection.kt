package com.stc.openweather.composable.widgets

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
import com.stc.openweather.util.capitalizeFirstLetter
import com.stc.openweather.util.limitToOneDecimal

@Composable
fun CurrentWeatherScreen(
    date: String,
    iconId: String,
    temp: Double,
    feelsLike: Double,
    description: String
) {
    val iconUrl = iconId.let {
        "https://openweathermap.org/img/wn/$it@4x.png"
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = date,
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
                text = "${temp.limitToOneDecimal()}°C",
                style = MaterialTheme.typography.displayMedium, // Larger font for temperature
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        Text(
            text = description.capitalizeFirstLetter(),
            style = MaterialTheme.typography.titleMedium, // Slightly smaller font
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Wind speed
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = "Feels like: ${feelsLike.limitToOneDecimal()}°C",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
