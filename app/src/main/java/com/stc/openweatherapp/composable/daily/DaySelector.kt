package com.stc.openweatherapp.composable.daily

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.stc.openweatherapp.R
import com.stc.openweatherapp.model.DailyWeather
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun DaySelector(
    dailyWeather: List<DailyWeather>,
    selectedDayIndex: Int,
    onDaySelected: (Int) -> Unit
) {
    val dayFormatter = SimpleDateFormat("EEE", Locale.getDefault())
    val listState = rememberLazyListState() // Remember the LazyListState

    // Scroll to the selected item when the Composable is first composed
    LaunchedEffect(selectedDayIndex) {
        listState.scrollToItem(selectedDayIndex)
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        state = listState, // Attach the LazyListState
        horizontalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        itemsIndexed(dailyWeather) { index, daily ->
            val isSelected = index == selectedDayIndex

            // Day label (e.g., Today, Thursday)
            val dayLabel = if (index == 0) {
                stringResource(R.string.today)
            } else {
                dayFormatter.format(Date(daily.dt * 1000))
            }

            val dayTemp = daily.temp.day.roundToInt()
            val nightTemp = daily.temp.night.roundToInt()

            val iconUrl = daily.weather.firstOrNull()?.icon?.let { iconId ->
                "https://openweathermap.org/img/wn/$iconId@2x.png"
            }

            Card(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .clip(CardDefaults.shape)
                    .clickable { onDaySelected(index) },
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.surfaceContainerHigh
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .width(60.dp)
                ) {
                    // Day label
                    Text(
                        text = dayLabel,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
                        else MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Weather icon
                    iconUrl?.let {
                        AsyncImage(
                            model = it,
                            contentDescription = "Weather Icon",
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Day and Night temperatures
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$dayTemp°/",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
                            else MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "$nightTemp°",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}