package com.stc.openweatherapp.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stc.openweatherapp.model.DailyWeather

@Composable
fun DailyWeatherList(
    dailyList: List<DailyWeather>,
    onDailyItemClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {


        // Display each day in a minimal, compact row
        dailyList.forEachIndexed { index, daily ->
            DailyWeatherItem(
                daily = daily,
                isToday = (index == 0),
                onClick = { onDailyItemClick(index) }
            )

            // Divider between items (except after the last)
            if (index < dailyList.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}