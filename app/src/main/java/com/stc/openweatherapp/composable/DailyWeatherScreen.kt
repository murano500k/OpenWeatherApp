package com.stc.openweatherapp.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stc.openweatherapp.viewmodel.WeatherViewModel
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DailyWeatherScreen(
    viewModel: WeatherViewModel,
    dayIndex: Int
) {
    // State to track which day is currently selected
    val dailyList = viewModel.weatherData.value?.daily ?: emptyList()
    val dayCount = dailyList.size

    // Index of the currently selected day
    var selectedDayIndex by remember { mutableIntStateOf(dayIndex) }

    // Loading state from the ViewModel
    val isRefreshing by viewModel.loading.observeAsState(false)

    // Pull-to-refresh state
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            // Trigger the refresh logic in the ViewModel
            viewModel.fetchWeatherData()
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState) // Add pull-to-refresh behavior
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Top: Day Selector (Dropdown or something similar)
            DaySelector(
                dayCount = dayCount,
                selectedDayIndex = selectedDayIndex,
                onDaySelected = { newIndex -> selectedDayIndex = newIndex }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // The selected day
            val selectedDay = dailyList[selectedDayIndex]

            // Display all daily details for this day
            DailyWeatherContent(selectedDay)
        }

        // Pull-to-refresh indicator
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}