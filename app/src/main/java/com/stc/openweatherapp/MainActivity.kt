package com.stc.openweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.stc.openweatherapp.composable.WeatherScreen
import com.stc.openweatherapp.ui.theme.OpenWeatherAppTheme
import com.stc.openweatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val viewModel: WeatherViewModel by viewModels()
    companion object {
        private const val DEFAULT_CITY = "Warsaw"
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getUserLocation()
        } else {
            Timber.e("Location permission denied")
        }
    }

    private fun getUserLocation() {
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    Timber.d("Location: ${location.latitude}, ${location.longitude}")
                    viewModel.fetchCityByCoordinates(location.latitude, location.longitude)
                    viewModel.fetchWeatherData(location.latitude, location.longitude)
                } else {
                    Timber.e("Unable to fetch location")
                }
            }
        } catch (e: SecurityException) {
            Timber.e("Permission not granted for location: ${e.message}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OpenWeatherAppTheme {
                WeatherScreen()
            }
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationPermissionRequest.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)

        viewModel.coordinates.observe(this) { coordinates ->
            if(coordinates.isNotEmpty()) {
                Timber.d("Coordinates: ${coordinates[0].lat}, ${coordinates[0].lon}")
            }
        }

        viewModel.weatherData.observe(this) { weatherResponse ->
            Timber.d("Current temp: ${weatherResponse.current.temp}")
        }

        viewModel.cityInfo.observe(this) { cityInfoList ->
            if(cityInfoList.isNotEmpty()) {
                Timber.d("City info: ${cityInfoList[0].name}, ${cityInfoList[0].country}")
            }
        }

        viewModel.fetchCityCoordinates(DEFAULT_CITY)
    }
}

