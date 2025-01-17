package com.stc.openweatherapp.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.stc.openweatherapp.model.DailyWeather

@Composable
fun DailyWeatherList(
    dailyList: List<DailyWeather>,
    onDailyItemClick: (Int) -> Unit
) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .clip(CardDefaults.shape)
            .clip(CardDefaults.shape),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "Daily Forecast",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )
            dailyList.forEachIndexed { index, daily ->
                DailyWeatherItem(
                    daily = daily,
                    isToday = (index == 0),
                    onClick = { onDailyItemClick(index) }
                )

                // Divider between items (except after the last)
                if (index < dailyList.lastIndex) {
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        }
    }

}