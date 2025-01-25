package com.stc.openweatherapp.composable.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HumidityCard(
    humidity: Int,
    dewPoint: Double,
    modifier: Modifier
) {
    // Format dew point (e.g., "14.5")
    val dewPointStr = String.format("%.1f", dewPoint)

    Card(
        modifier = modifier
    ) {
        Column {

            Text(
                text = "Humidity",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$humidity%",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Dew point: $dewPointStr°C",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}