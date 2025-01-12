package com.stc.openweatherapp.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stc.openweatherapp.viewmodel.WeatherViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
    onDailyItemClick: (Int) -> Unit
) {
    val locationError: String? by viewModel.locationError.observeAsState(null)
    val cityInfo by viewModel.cityInfo.observeAsState()
    var query: String by remember { mutableStateOf("") }
    val weatherData by viewModel.weatherData.observeAsState()
    val isLoading by viewModel.loading.observeAsState(false)
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = {
            viewModel.fetchWeatherData()
        }
    )


    cityInfo.let { city ->
        query = city?.name ?: ""
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState) // Add pull-to-refresh behavior
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(16.dp)
        ) {
            item {
                SearchBox(
                    query = query,
                    onQueryChanged = { query = it },
                    onSearch = {
                        viewModel.fetchCityCoordinates(query)
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }


            locationError?.let { errorMessage ->
                item {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
            weatherData?.let { weather ->
                item {
                    CurrentWeatherScreen(currentWeather = weather.current)
                }
                item {
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Text(
                        text = "Hourly Forecast",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                item {
                    HourlyWeatherScreen(hourlyWeatherList = weather.hourly)
                }
                item {
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                item {
                    // Title or header for the card
                    Text(
                        text = "Daily Forecast",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                item {
                    DailyWeatherList(
                        weather.daily,
                        onDailyItemClick = onDailyItemClick
                    )
                }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        WindCard(
                            windSpeed = weather.current.wind_speed,
                            windDeg = weather.current.wind_deg
                        )
                        HumidityCard(
                            humidity = weather.current.humidity,
                            dewPoint = weather.current.dew_point
                        )
                        UviCard(uvi = weather.current.uvi)
                        PressureCard(pressure = weather.current.pressure)
                        SunCard(weather.current.sunrise, weather.current.sunset)
                    }
                }
            }

            if (isLoading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center)
                            .size(48.dp)
                    )
                }
            }
        }
        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }


}


