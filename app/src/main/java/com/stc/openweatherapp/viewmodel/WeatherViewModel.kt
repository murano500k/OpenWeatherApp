package com.stc.openweatherapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.stc.openweatherapp.R
import com.stc.openweatherapp.model.CityCoordinates
import com.stc.openweatherapp.model.CityInfo
import com.stc.openweatherapp.model.WeatherResponse
import com.stc.openweatherapp.repository.WeatherApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val weatherApiService: WeatherApiService,
    private val fusedLocationClient: FusedLocationProviderClient
) : ViewModel() {


    private val _coordinates = MutableLiveData<List<CityCoordinates>>()
    val coordinates: LiveData<List<CityCoordinates>> get() = _coordinates

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> get() = _weatherData

    private val _cityInfo = MutableLiveData<List<CityInfo>>()
    val cityInfo: LiveData<List<CityInfo>> get() = _cityInfo

    private val _locationError = MutableLiveData<String?>()
    val locationError: LiveData<String?> get() = _locationError

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading

    fun fetchCityCoordinates(cityName: String) {
        viewModelScope.launch {
            try {
                val result = weatherApiService.getCityCoordinates(cityName)
                if (result.isNotEmpty()) {
                    _coordinates.postValue(result)
                    _locationError.postValue(null)
                    fetchWeatherData(result[0].lat, result[0].lon)
                } else {
                    val errorMessage = context.getString(R.string.error_no_coordinates, cityName)
                    _locationError.postValue(errorMessage)
                    Timber.e(errorMessage)
                }
            } catch (e: Exception) {
                val errorMessage = context.getString(R.string.error_fetching_coordinates, cityName)
                _locationError.postValue(errorMessage)
                Timber.e(e, errorMessage)
            }
        }
    }

    fun fetchCityByCoordinates(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val result = weatherApiService.getCityByCoordinates(lat, lon)
                _cityInfo.postValue(result)
            } catch (e: Exception) {
                Timber.e(e, "Error fetching city info")
            }
        }
    }

    fun fetchWeatherData(lat: Double, lon: Double, exclude: String? = null) {
        _loading.postValue(true) // Set loading to true when the request starts
        viewModelScope.launch {
            try {
                val response = weatherApiService.getWeatherData(lat, lon, exclude)
                _weatherData.postValue(response)
                _locationError.postValue(null) // Clear error on success
            } catch (e: Exception) {
                val errorMessage = context.getString(R.string.error_fetching_weather_data)
                _locationError.postValue(errorMessage)
                Timber.e(e, "Error fetching weather data")
            } finally {
                _loading.postValue(false) // Set loading to false when the request ends
            }
        }
    }

    fun fetchUserLocation() {
        _loading.postValue(true) // Set loading to true when the request starts
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    fetchCityByCoordinates(location.latitude, location.longitude)
                    fetchWeatherData(location.latitude, location.longitude)
                } else {
                    _locationError.postValue("Unable to fetch location")
                }
            }
        } catch (e: SecurityException) {
            _locationError.postValue("Permission not granted for location")
        } finally {
            _loading.postValue(false) // Set loading to false when the request ends
        }
    }
}