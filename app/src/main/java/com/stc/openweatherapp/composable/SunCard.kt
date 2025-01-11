package com.stc.openweatherapp.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.stc.openweatherapp.R
import java.util.Date

@Composable
fun SunCard(
    sunrise: Long, // UNIX timestamp in seconds
    sunset: Long   // UNIX timestamp in seconds
) {
    val context = LocalContext.current

    // Use device 12/24-hour preference
    val timeFormat = remember {
        android.text.format.DateFormat.getTimeFormat(context)
    }

    // Format sunrise/sunset time
    val sunriseTime = remember(sunrise) {
        timeFormat.format(Date(sunrise * 1000))
    }
    val sunsetTime = remember(sunset) {
        timeFormat.format(Date(sunset * 1000))
    }

    val sunriseColor = colorResource(id = R.color.sunrise_orange)
    val sunsetColor = colorResource(id = R.color.sunset_dark_blue)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = "Sunrise",
                    style = MaterialTheme.typography.titleSmall,
                    color = sunriseColor
                )
                Text(
                    text = sunriseTime,
                    style = MaterialTheme.typography.bodyMedium,
                    color = sunriseColor
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "Sunset",
                    style = MaterialTheme.typography.titleSmall,
                    color = sunsetColor
                )
                Text(
                    text = sunsetTime,
                    style = MaterialTheme.typography.bodyMedium,
                    color = sunsetColor
                )
            }
        }
    }
}