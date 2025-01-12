package com.stc.openweatherapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.stc.openweatherapp.composable.AppNavHost
import com.stc.openweatherapp.ui.theme.OpenWeatherAppTheme
import com.stc.openweatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    companion object {
        private const val DEFAULT_CITY = "Warsaw"
    }

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
                /*WeatherScreen(
                    onDailyItemClick = { dayIndex ->
                        Timber.w("clicked on $dayIndex")
                    }
                )*/
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

