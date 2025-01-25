package com.stc.openweather.composable.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stc.openweather.util.getUviAdvice
import com.stc.openweather.util.getUviCategory

@Composable
fun UviCard(uvi: Double, modifier: Modifier) {
    val uviCategory = getUviCategory(uvi)        // e.g. "High"
    val advice = getUviAdvice(uvi)              // e.g. "Cover up, use SPF 30+, ..."
    val displayUvi = String.format("%.1f", uvi) // Round to 1 decimal place

    Card(
        modifier = modifier
    ) {
        Column {
            Text(
                text = "UV Index",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = displayUvi,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = uviCategory,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Advice
            Text(
                text = advice,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
        }
    }
}