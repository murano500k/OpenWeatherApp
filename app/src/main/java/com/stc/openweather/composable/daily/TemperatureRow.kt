package com.stc.openweather.composable.daily

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.stc.openweather.R
import com.stc.openweather.model.TempItem
import kotlin.math.roundToInt

/**
 * A horizontally scrolling list showing morning, day, evening, and night
 * temperature & feels_like pairs.
 */
@Composable
fun TemperatureRow(
    mornTemp: Double,
    dayTemp: Double,
    eveTemp: Double,
    nightTemp: Double,
    mornFeels: Double,
    dayFeels: Double,
    eveFeels: Double,
    nightFeels: Double
) {

    val tempList = listOf(
        TempItem(stringResource(R.string.time_morning), mornTemp, mornFeels),
        TempItem(stringResource(R.string.time_day), dayTemp, dayFeels),
        TempItem(stringResource(R.string.time_evening), eveTemp, eveFeels),
        TempItem(stringResource(R.string.time_night), nightTemp, nightFeels)
    )
    Card(
        modifier = Modifier
            .clip(CardDefaults.shape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp)
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(tempList) { item ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${item.temp.roundToInt()}°C",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${item.feels.roundToInt()}°C",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}