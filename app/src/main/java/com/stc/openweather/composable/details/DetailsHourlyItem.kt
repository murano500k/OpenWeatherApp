package com.stc.openweather.composable.details

import DetailsType
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.stc.openweather.R
import com.stc.openweather.composable.elements.DropletIcon
import com.stc.openweather.model.HourlyWeather
import com.stc.openweather.util.formatLocalTime
import kotlin.math.roundToInt

@Composable
fun DetailsHourlyItem(
    type: DetailsType,
    hourlyWeather: HourlyWeather,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val time = remember(hourlyWeather.dt) {
        context.formatLocalTime(hourlyWeather.dt * 1000)
    }

    val text = when (type) {
        DetailsType.Humidity -> "${hourlyWeather.humidity}%"
        DetailsType.Precipitation -> "${(hourlyWeather.pop * 100).toInt()}%"
        DetailsType.Wind -> "${hourlyWeather.wind_speed.roundToInt()} m/s"
        else -> ""
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        when (type) {
            DetailsType.Humidity ->
                DropletIcon(
                    modifier = Modifier.size(40.dp),
                    humidity = hourlyWeather.humidity
                )

            DetailsType.Precipitation -> {
                val iconUrl = hourlyWeather.weather.firstOrNull()?.icon?.let { iconId ->
                    "https://openweathermap.org/img/wn/$iconId@4x.png"
                }
                if (iconUrl != null) {
                    AsyncImage(
                        model = iconUrl,
                        contentDescription = "Weather Icon",
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            DetailsType.Wind -> {
                val arrowRotation = (hourlyWeather.wind_deg + 180) % 360
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_up),
                    contentDescription = "Wind Arrow",
                    modifier = Modifier
                        .size(40.dp)
                        .rotate(arrowRotation.toFloat()),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            else -> {}
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = time,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary,
        )
    }
}