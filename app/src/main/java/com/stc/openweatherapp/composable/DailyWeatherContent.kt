package com.stc.openweatherapp.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stc.openweatherapp.model.DailyWeather
import com.stc.openweatherapp.model.TempItem
import com.stc.openweatherapp.util.round

@Composable
fun DailyWeatherContent(daily: DailyWeather) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
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
            dewPoint = daily.dew_point
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

        // 7. Summary Card (optional)
        SummaryCard(summary = daily.summary)
    }
}

/**
 * A horizontally scrolling list showing morning, day, evening, and night
 * temperature & feels_like pairs.
 */
@Composable
fun TemperatureRow(
    mornTemp: Double,
    dayTemp: Double,
    eveTemp: Double,
    nightTemp: Double,
    mornFeels: Double,
    dayFeels: Double,
    eveFeels: Double,
    nightFeels: Double
) {

    val tempList = listOf(
        TempItem("Morning", mornTemp, mornFeels),
        TempItem("Day", dayTemp, dayFeels),
        TempItem("Evening", eveTemp, eveFeels),
        TempItem("Night", nightTemp, nightFeels)
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(tempList) { item ->
            TemperatureItem(item)
        }
    }
}

/**
 * A single item in the horizontally scrolling row for temperature & feels_like.
 */
@Composable
fun TemperatureItem(item: TempItem) {
    // You can style this item in a Card or just a Column
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .width(120.dp) // Set a width so items look consistent
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = item.label,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Temp: ${item.temp.round()}°C",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Feels: ${item.feels.round()}°C",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}
