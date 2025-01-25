package com.stc.openweatherapp.composable.daily

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.stc.openweatherapp.PrecipitationCard
import com.stc.openweatherapp.R
import com.stc.openweatherapp.composable.widgets.CurrentWeatherScreen
import com.stc.openweatherapp.composable.widgets.HumidityCard
import com.stc.openweatherapp.composable.widgets.PressureCard
import com.stc.openweatherapp.composable.widgets.SunCard
import com.stc.openweatherapp.composable.widgets.UviCard
import com.stc.openweatherapp.composable.widgets.WindCard
import com.stc.openweatherapp.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)
@Composable
fun DailyWeatherScreen(
    viewModel: WeatherViewModel,
    dayIndex: Int,
    navController: NavController
) {
    val dailyList = viewModel.weatherData.value?.daily ?: emptyList()
    var selectedDayIndex by remember { mutableIntStateOf(dayIndex) }
    val isRefreshing by viewModel.loading.observeAsState(false)

    // Pull-to-refresh state
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            viewModel.fetchWeatherData()
            navController.popBackStack()
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.daily_weather_screen_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Respect inner padding from Scaffold
                .pullRefresh(pullRefreshState) // Add pull-to-refresh behavior inside Scaffold content
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                // Day Selector
                item {
                    DaySelector(
                        dailyWeather = dailyList,
                        selectedDayIndex = selectedDayIndex,
                        onDaySelected = { newIndex -> selectedDayIndex = newIndex }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Selected Day Content
                val selectedDay = dailyList[selectedDayIndex]
                val dateText = if (selectedDayIndex == 0) {
                    "Today"
                } else {
                    val dateFormat = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
                    dateFormat.format(Date(selectedDay.dt * 1000))
                }
                item {
                    CurrentWeatherScreen(
                        date = dateText,
                        iconId = selectedDay.weather[0].icon,
                        temp = selectedDay.temp.day,
                        feelsLike = selectedDay.feels_like.day,
                        description = selectedDay.weather[0].description
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    TemperatureRow(
                        mornTemp = selectedDay.temp.morn,
                        dayTemp = selectedDay.temp.day,
                        eveTemp = selectedDay.temp.eve,
                        nightTemp = selectedDay.temp.night,
                        mornFeels = selectedDay.feels_like.morn,
                        dayFeels = selectedDay.feels_like.day,
                        eveFeels = selectedDay.feels_like.eve,
                        nightFeels = selectedDay.feels_like.night
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    FlowRow(
                        maxItemsInEachRow = 2,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        val itemModifier = Modifier
                            .height(160.dp)
                            .weight(1f)
                            .clip(CardDefaults.shape)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(16.dp)

                        PrecipitationCard(
                            selectedDay.pop,
                            modifier = itemModifier
                        )

                        WindCard(
                            selectedDay.wind_speed,
                            selectedDay.wind_deg,
                            modifier = itemModifier
                        )
                        HumidityCard(
                            humidity = selectedDay.humidity,
                            dewPoint = selectedDay.dew_point,
                            modifier = itemModifier
                        )
                        UviCard(uvi = selectedDay.uvi,
                            modifier = itemModifier)
                        PressureCard(pressure = selectedDay.pressure,
                            modifier = itemModifier)
                        SunCard(selectedDay.sunrise, selectedDay.sunset,
                            modifier = itemModifier)
                    }
                }
            }

            // Pull-to-refresh indicator
            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}