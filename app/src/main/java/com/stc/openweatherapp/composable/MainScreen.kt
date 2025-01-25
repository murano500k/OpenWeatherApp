package com.stc.openweatherapp.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.stc.openweatherapp.composable.details.DetailsCard
import com.stc.openweatherapp.composable.widgets.SearchBox
import com.stc.openweatherapp.composable.hourly.HourlyWeatherScreen
import com.stc.openweatherapp.composable.widgets.CurrentWeatherScreen
import com.stc.openweatherapp.composable.widgets.DailyWeatherList
import com.stc.openweatherapp.composable.widgets.HumidityCard
import com.stc.openweatherapp.composable.widgets.PressureCard
import com.stc.openweatherapp.composable.widgets.SunCard
import com.stc.openweatherapp.composable.widgets.UviCard
import com.stc.openweatherapp.viewmodel.WeatherViewModel


@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
    onDailyItemClick: (Int) -> Unit,
) {
    val locationError: String? by viewModel.locationError.observeAsState(null)
    val cityInfo by viewModel.cityInfo.observeAsState()
    var query: String by remember { mutableStateOf("") }
    val weatherData by viewModel.weatherData.observeAsState()
    val isLoading by viewModel.loading.observeAsState(false)
    var collapseHourlyWeather by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = {
            viewModel.fetchWeatherData()
            collapseHourlyWeather = true
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
                    CurrentWeatherScreen(
                        date = "Now",
                        iconId = weather.current.weather[0].icon,
                        temp = weather.current.temp,
                        feelsLike = weather.current.feels_like,
                        description = weather.current.weather[0].description
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    HourlyWeatherScreen(
                        hourlyWeatherList = weather.hourly,
                        collapseAll = collapseHourlyWeather
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    DailyWeatherList(
                        weather.daily,
                        onDailyItemClick = onDailyItemClick
                    )
                }
                item {
                    FlowRow(
                        maxItemsInEachRow = 2,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        val itemModifier = Modifier
                            .height(160.dp)
                            .weight(1f)
                            .clip(CardDefaults.shape)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(16.dp)
                        HumidityCard(
                            humidity = weather.current.humidity,
                            dewPoint = weather.current.dew_point,
                            modifier = itemModifier
                        )
                        UviCard(uvi = weather.current.uvi,
                            modifier = itemModifier)
                        PressureCard(pressure = weather.current.pressure,
                            modifier = itemModifier)
                        SunCard(weather.current.sunrise, weather.current.sunset,
                            modifier = itemModifier)
                    }

                }
                item {
                    DetailsCard(viewModel)
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


