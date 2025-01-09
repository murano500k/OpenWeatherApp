package com.stc.openweatherapp.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stc.openweatherapp.viewmodel.WeatherViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    val locationError: String? by viewModel.locationError.observeAsState(null)
    val cityInfo by viewModel.cityInfo.observeAsState(emptyList())
    var query: String by remember { mutableStateOf("") }

    if (cityInfo.isNotEmpty()) {
        query = cityInfo[0].name // Automatically update the search box with the city name
    }

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

        item {
            Text(
                text = "Hourly Forecast",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}


