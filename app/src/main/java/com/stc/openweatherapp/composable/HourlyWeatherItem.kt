package com.stc.openweatherapp.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.stc.openweatherapp.model.HourlyWeather
import java.util.Date
import kotlin.math.roundToInt

@Composable
fun HourlyWeatherItem(
    hourlyWeather: HourlyWeather,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current

    // Format hour using the device's time format
    val hour = remember(hourlyWeather.dt) {
        val timeFormat = android.text.format.DateFormat.getTimeFormat(context)
        timeFormat.format(Date(hourlyWeather.dt * 1000))
    }

    // Weather icon URL
    val iconUrl = hourlyWeather.weather.firstOrNull()?.icon?.let { iconId ->
        "https://openweathermap.org/img/wn/$iconId@4x.png"
    }

    val dayBackgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
    val selectedBackgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)

    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) selectedBackgroundColor else dayBackgroundColor)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.4f
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(60.dp) // Maintain uniform width
        ) {
            // Weather Icon
            if (iconUrl != null) {
                AsyncImage(
                    model = iconUrl,
                    contentDescription = "Weather Icon",
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Temperature
            Text(
                text = "${hourlyWeather.temp.roundToInt()}°C",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Probability of Rain/Snow
            Text(
                text = "${(hourlyWeather.pop * 100).toInt()}%",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Hour
            Text(
                text = hour,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}