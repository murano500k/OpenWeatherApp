package com.stc.openweatherapp.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.stc.openweatherapp.model.HourlyWeather

@Composable
fun HourlyWeatherScreen(
    hourlyWeatherList: List<HourlyWeather>,
    collapseAll: Boolean
) {
    val expandedStates = remember(hourlyWeatherList) {
        mutableStateListOf(*Array(hourlyWeatherList.size) { false })
    }

    if (collapseAll) {
        expandedStates.fill(false) // Collapse all items
    }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .clip(CardDefaults.shape)
            .clip(CardDefaults.shape),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column {
            Text(
                text = "Hourly Forecast",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                itemsIndexed(hourlyWeatherList) { index, hourlyWeather ->
                    HourlyWeatherItem(hourlyWeather = hourlyWeather,
                        expanded = expandedStates[index],
                        onExpandChange = { expandedStates[index] = it }
                    )
                }
            }
        }
    }
}