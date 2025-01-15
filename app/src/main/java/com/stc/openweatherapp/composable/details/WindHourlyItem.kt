package com.stc.openweatherapp.composable.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.stc.openweatherapp.R
import com.stc.openweatherapp.model.HourlyWeather
import com.stc.openweatherapp.util.windDegreesToDirection
import java.util.Date
import kotlin.math.roundToInt

@Composable
fun WindHourlyItem(hourlyWeather: HourlyWeather) {
    val context = LocalContext.current

    // Format time (e.g., "Now," "21:00")
    val time = remember(hourlyWeather.dt) {
        val now = System.currentTimeMillis() / 1000
        if (hourlyWeather.dt == now) {
            context.getString(R.string.now)
        } else {
            val timeFormat = android.text.format.DateFormat.getTimeFormat(context)
            timeFormat.format(Date(hourlyWeather.dt * 1000)) // Convert Unix time to milliseconds
        }
    }

    // Wind direction and arrow rotation
    val windDirection = hourlyWeather.wind_deg.windDegreesToDirection()
    val arrowRotation = (hourlyWeather.wind_deg + 180) % 360

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp) // Fixed width for uniformity
            .padding(vertical = 4.dp)
    ) {
        // Arrow icon rotated based on wind direction
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_up),
            contentDescription = "Wind Arrow",
            modifier = Modifier
                .size(40.dp)
                .rotate(arrowRotation.toFloat()),
            tint = MaterialTheme.colorScheme.primary
        )

        // Wind direction text
        Text(
            text = windDirection,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 4.dp)
        )

        // Wind speed
        Text(
            text = "${hourlyWeather.wind_speed.roundToInt()} m/s",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 4.dp)
        )

        // Time
        Text(
            text = time,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}