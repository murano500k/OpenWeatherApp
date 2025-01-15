package com.stc.openweatherapp.composable.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.stc.openweatherapp.R
import com.stc.openweatherapp.model.HourlyWeather
import java.util.Date

@Composable
fun HumidityHourlyItem(hourlyWeather: HourlyWeather) {
    val context = LocalContext.current

    val time = remember(hourlyWeather.dt) {
        val now = System.currentTimeMillis() / 1000
        if (hourlyWeather.dt == now) {
            context.getString(R.string.now)
        } else {
            val timeFormat = android.text.format.DateFormat.getTimeFormat(context)
            timeFormat.format(Date(hourlyWeather.dt * 1000))
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp) // Fixed width for uniformity
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "${hourlyWeather.humidity}%",
            style = MaterialTheme.typography.bodySmall,
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