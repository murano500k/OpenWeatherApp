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
import com.stc.openweatherapp.util.getPressureDescription
import com.stc.openweatherapp.util.toBarString

@Composable
fun PressureCard(pressure: Int, modifier: Modifier) {
    val pressureDescription = getPressureDescription(pressure)

    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
        ) {
            // Title text
            Text(
                text = "Pressure",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${pressure.toBarString()} Bar",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = pressureDescription,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

        }
    }
}