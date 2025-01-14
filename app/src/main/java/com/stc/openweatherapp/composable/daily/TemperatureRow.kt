package com.stc.openweatherapp.composable.daily

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stc.openweatherapp.model.TempItem

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
        TempItem("Morning", mornTemp, mornFeels),
        TempItem("Day", dayTemp, dayFeels),
        TempItem("Evening", eveTemp, eveFeels),
        TempItem("Night", nightTemp, nightFeels)
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        items(tempList) { item ->
            TemperatureItem(item)
        }
    }
}