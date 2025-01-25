package com.stc.openweather

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.stc.openweather.composable.navigation.AppNavHost
import com.stc.openweather.ui.theme.OpenWeatherAppTheme
import com.stc.openweather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.fetchUserLocation()
        } else {
            Toast.makeText(
                this,
                getString(R.string.location_permission_denied),
                Toast.LENGTH_LONG
            ).show()
            Timber.e("Location permission denied")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OpenWeatherAppTheme {
                AppNavHost()
            }
        }

        locationPermissionRequest.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)

        viewModel.weatherData.observe(this) { weatherResponse ->
            Timber.d("Current temp: ${weatherResponse.current.temp}")
        }

        viewModel.locationError.observe(this) { error ->
            if (error != null) {
                Timber.e("Location Error: $error")
            }
        }
    }
}

