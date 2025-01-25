package com.stc.openweather.composable.hourly

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.stc.openweather.R
import com.stc.openweather.model.HourlyWeather
import com.stc.openweather.util.formatLocalTime
import com.stc.openweather.util.getUviCategory
import kotlin.math.roundToInt

@Composable
fun HourlyWeatherItem(
    hourlyWeather: HourlyWeather,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit
) {
    val context = LocalContext.current

    // Format hour using the device's time format
    val hour = remember(hourlyWeather.dt) {
        context.formatLocalTime(hourlyWeather.dt * 1000)
    }

    // Weather icon URL
    val iconUrl = hourlyWeather.weather.firstOrNull()?.icon?.let { iconId ->
         stringResource(R.string.weather_icon_url, iconId)
    }

    Card(
        modifier = Modifier
            .clip(CardDefaults.shape)
            .clickable { onExpandChange(!expanded) }
            .animateContentSize()
            .clip(CardDefaults.shape),
        colors = CardDefaults.cardColors(
            containerColor = if (expanded) MaterialTheme.colorScheme.secondaryContainer
            else MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(40.dp)
                    .padding(vertical = 8.dp)
            ) {
                // Weather Icon
                if (iconUrl != null) {
                    AsyncImage(
                        model = iconUrl,
                        contentDescription = "Weather Icon",
                        modifier = Modifier.size(50.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Temperature
                Text(
                    text = "${hourlyWeather.temp.roundToInt()}°C",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Probability of Rain/Snow
                Text(
                    text = "${(hourlyWeather.pop * 100).toInt()}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Hour
                Text(
                    text = hour,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            AnimatedVisibility(expanded) {
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Feels like: ${hourlyWeather.feels_like.roundToInt()}°C",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Wind: ${hourlyWeather.wind_speed.roundToInt()}m/s",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Humidity: ${hourlyWeather.humidity}%",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "UVI: ${getUviCategory(LocalContext.current, hourlyWeather.uvi)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Visibility: ${hourlyWeather.visibility / 1000} km",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }

    }
}