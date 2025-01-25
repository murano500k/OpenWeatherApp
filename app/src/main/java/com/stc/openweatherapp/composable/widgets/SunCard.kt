package com.stc.openweatherapp.composable.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.stc.openweatherapp.R
import com.stc.openweatherapp.util.formatLocalTime

@Composable
fun SunCard(
    sunrise: Long,
    sunset: Long,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Format sunrise/sunset time
    val sunriseTime = remember(sunrise) {
        context.formatLocalTime(sunrise * 1000)
    }
    val sunsetTime = remember(sunset) {
        context.formatLocalTime(sunset * 1000)
    }

    val sunriseColor = colorResource(id = R.color.sunrise_orange)
    val sunsetColor = colorResource(id = R.color.sunset_dark_blue)
    // Get current time in seconds
    val currentTime = System.currentTimeMillis() / 1000

    // Determine whether to show time till sunrise or sunset
    val (timeLabel, targetTime) = if (currentTime < sunrise) {
        stringResource(R.string.sunrise) to sunrise
    } else if (currentTime < sunset) {
        stringResource(R.string.sunset) to sunset
    } else {
        stringResource(R.string.sunrise) to sunrise + 24 * 3600 // Next sunrise
    }

    // Calculate the time difference
    val timeDifference = targetTime - currentTime
    val hours = timeDifference / 3600
    val minutes = (timeDifference % 3600) / 60

    val timeDisplay = String.format("%02d:%02d", hours, minutes)

    Card(
        modifier = modifier
    ) {

        Column(
            modifier = Modifier
        ) {
            Text(
                text = "Sunrise",
                style = MaterialTheme.typography.titleMedium,
                color = sunriseColor
            )
            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = sunriseTime,
                style = MaterialTheme.typography.bodyLarge,
                color = sunriseColor
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Sunset",
                style = MaterialTheme.typography.titleMedium,
                color = sunsetColor
            )
            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = sunsetTime,
                style = MaterialTheme.typography.bodyLarge,
                color = sunsetColor
            )
        }
    }
}