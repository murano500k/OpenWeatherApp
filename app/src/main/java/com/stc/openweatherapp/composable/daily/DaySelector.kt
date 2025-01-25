package com.stc.openweatherapp.composable.daily

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.stc.openweatherapp.model.DailyWeather

@Composable
fun DaySelector(
    dailyWeather: List<DailyWeather>,
    selectedDayIndex: Int,
    onDaySelected: (Int) -> Unit
) {
    val listState = rememberLazyListState() // Remember the LazyListState

    // Scroll to the selected item when the Composable is first composed
    LaunchedEffect(selectedDayIndex) {
        listState.scrollToItem(selectedDayIndex)
    }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .clip(CardDefaults.shape),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            state = listState, // Attach the LazyListState
            horizontalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            itemsIndexed(dailyWeather) { index, daily ->
                DaySelectorItem(
                    index = index,
                    daily = daily,
                    isSelected = index == selectedDayIndex,
                    onDaySelected = onDaySelected
                )
            }
        }
    }
}