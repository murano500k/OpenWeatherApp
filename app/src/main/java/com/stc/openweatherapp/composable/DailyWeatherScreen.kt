package com.stc.openweatherapp.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stc.openweatherapp.viewmodel.WeatherViewModel

@Composable
fun DailyWeatherScreen(
    viewModel: WeatherViewModel = viewModel(), dayIndex: Int) {
    // State to track which day is currently selected
    val dailyList = viewModel.weatherData.value?.daily ?: emptyList()
    val dayCount = dailyList.size

    // Index of the currently selected day
    var selectedDayIndex by remember { mutableIntStateOf(dayIndex) }

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
}
