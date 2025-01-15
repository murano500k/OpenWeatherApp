package com.stc.openweatherapp.composable.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.stc.openweatherapp.R
import com.stc.openweatherapp.composable.details.DetailsType.HUMIDITY
import com.stc.openweatherapp.composable.details.DetailsType.PRECIPITATION
import com.stc.openweatherapp.composable.details.DetailsType.WIND
import com.stc.openweatherapp.viewmodel.WeatherViewModel


object DetailsType {
    const val PRECIPITATION = "Precipitation"
    const val WIND = "Wind"
    const val HUMIDITY = "Humidity"
}

@Composable
fun DetailsCard(
    viewModel: WeatherViewModel,
) {
    val weatherData by viewModel.weatherData.observeAsState()
    val hourlyWeatherList = weatherData?.hourly ?: emptyList()
    var selectedType by remember { mutableStateOf(WIND) }
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(8.dp), // Rounded corners
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer // Background color
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Top: Buttons for Wind and Humidity
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                TextButton(
                    onClick = { selectedType = PRECIPITATION },
                    modifier = Modifier
                        .padding(4.dp)
                        .background(
                            if (selectedType == PRECIPITATION)
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                            else
                                Color.Transparent,
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_precipitation),
                        contentDescription = "Precipitation Icon",
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(id = R.string.precipitation),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                // Wind Button
                TextButton(
                    onClick = { selectedType = WIND },
                    modifier = Modifier
                        .padding(4.dp)
                        .background(
                            if (selectedType == WIND)
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                            else
                                Color.Transparent,
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_wind),
                        contentDescription = "Wind Icon",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(id = R.string.wind),
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                // Humidity Button
                TextButton(
                    onClick = { selectedType = HUMIDITY },
                    modifier = Modifier
                        .padding(4.dp)
                        .background(
                            if (selectedType == HUMIDITY)
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                            else
                                Color.Transparent,
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_humidity),
                        contentDescription = "Humidity Icon",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(id = R.string.humidity),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            // Horizontal Scrolling List
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                when (selectedType) {
                    WIND -> {
                        items(hourlyWeatherList) { hourlyWeather ->
                            WindHourlyItem(hourlyWeather = hourlyWeather)
                        }
                    }

                    PRECIPITATION -> {
                        items(hourlyWeatherList) { hourlyWeather ->
                            PrecipitationHourlyItem(hourlyWeather = hourlyWeather)
                        }
                    }

                    HUMIDITY -> {
                        items(hourlyWeatherList) { hourlyWeather ->
                            HumidityHourlyItem(hourlyWeather = hourlyWeather)
                        }
                    }
                }
            }
        }
    }

}