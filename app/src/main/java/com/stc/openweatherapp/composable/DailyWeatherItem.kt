package com.stc.openweatherapp.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.stc.openweatherapp.model.DailyWeather
import com.stc.openweatherapp.util.round
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DailyWeatherItem(
    daily: DailyWeather,
    isToday: Boolean = false,
    onClick: () -> Unit
) {
    val dateText = remember(daily.dt) {
        if (isToday) {
            "Today"
        } else {
            val dateFormat = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
            dateFormat.format(Date(daily.dt * 1000))
        }
    }

    val popPercent = ((daily.pop) * 100).toInt()

    val iconUrl = daily.weather.firstOrNull()?.icon?.let { iconId ->
        "https://openweathermap.org/img/wn/$iconId@2x.png"
    }

    val dayTemp = daily.temp.day.round()
    val nightTemp = daily.temp.night.round()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),

        verticalAlignment = Alignment.CenterVertically,
    ) {
        // LEFT SECTION: Date & Precipitation in one horizontal line
        Row(
            modifier = Modifier.weight(1.5f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = dateText,
                style = if (isToday) {
                    MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    MaterialTheme.typography.titleMedium
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "$popPercent%",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
            )
        }

        // CENTER SECTION: Weather Icon
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            iconUrl?.let {
                AsyncImage(
                    model = it,
                    contentDescription = "Daily weather icon",
                    modifier = Modifier.size(50.dp)
                )
            }
        }

        // RIGHT SECTION: Day & Night Temperatures
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "$dayTemp°C",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = " / ",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "$nightTemp°C",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
