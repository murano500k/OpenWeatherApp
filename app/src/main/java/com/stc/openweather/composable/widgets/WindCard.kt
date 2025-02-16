package com.stc.openweather.composable.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.stc.openweather.R
import com.stc.openweather.util.getWindDescription
import com.stc.openweather.util.windDegreesToDirection

@Composable
fun WindCard(
    windSpeed: Double,
    windDeg: Int,
    modifier: Modifier = Modifier
) {
    // Convert degrees to a text direction
    val windDirectionText = windDeg.windDegreesToDirection(LocalContext.current)
    // Format wind speed with one decimal (e.g. "5.4")
    val displaySpeed = String.format("%.1f", windSpeed)
    // Brief description (e.g., "Moderate", "Strong")
    val windDescription = getWindDescription(LocalContext.current, windSpeed)

    // Calculate how much to rotate the arrow so it points "from" wind direction
    val arrowRotation = (windDeg + 180) % 360

    Card(
        modifier = modifier
    ) {
        Column {
            // Title: "Wind"
            Text(
                text = "Wind",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Row with the arrow icon and the wind speed
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_up),
                    contentDescription = "Wind Arrow",
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(arrowRotation.toFloat()),
                    tint = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "$displaySpeed m/s",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Wind power description
            Text(
                text = "$windDescription",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Direction text (e.g. "South-West")
            Text(
                text = "from $windDirectionText",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
        }
    }
}