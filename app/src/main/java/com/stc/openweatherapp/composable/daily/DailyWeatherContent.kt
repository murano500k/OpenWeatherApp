package com.stc.openweatherapp.composable.daily

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.stc.openweatherapp.composable.HumidityCard
import com.stc.openweatherapp.composable.PressureCard
import com.stc.openweatherapp.composable.SunCard
import com.stc.openweatherapp.composable.UviCard
import com.stc.openweatherapp.composable.WindCard
import com.stc.openweatherapp.model.DailyWeather

@Composable
fun DailyWeatherContent(daily: DailyWeather) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        val iconUrl = daily.weather.firstOrNull()?.icon?.let { iconId ->
            "https://openweathermap.org/img/wn/$iconId@4x.png"
        }
        val popPercent = (daily.pop * 100).toInt()

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center // Center icon and text
        ) {
            if (iconUrl != null) {
                AsyncImage(
                    model = iconUrl,
                    contentDescription = "Daily weather icon",
                    modifier = Modifier.size(100.dp) // Set a large size for the icon
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "$popPercent%",
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (!daily.summary.isNullOrEmpty()) {
            Text(
                text = daily.summary,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(16.dp)
            )
        }

        // 1. Horizontal Scrolling List of Temp/Feels Like
        TemperatureRow(
            mornTemp = daily.temp.morn,
            dayTemp = daily.temp.day,
            eveTemp = daily.temp.eve,
            nightTemp = daily.temp.night,
            mornFeels = daily.feels_like.morn,
            dayFeels = daily.feels_like.day,
            eveFeels = daily.feels_like.eve,
            nightFeels = daily.feels_like.night
        )

        // 2. Wind Card
        WindCard(
            windSpeed = daily.wind_speed,
            windDeg = daily.wind_deg
        )

        // 3. Pressure Card
        PressureCard(
            pressure = daily.pressure
        )

        // 4. Humidity Card
        HumidityCard(
            humidity = daily.humidity,
            dewPoint = daily.dew_point,
            modifier = Modifier.Companion.clickable { }
        )

        // 5. UVI Card
        UviCard(
            uvi = daily.uvi
        )

        // 6. Sun Card (sunrise/sunset)
        SunCard(
            sunrise = daily.sunrise,
            sunset = daily.sunset
        )
    }
}




