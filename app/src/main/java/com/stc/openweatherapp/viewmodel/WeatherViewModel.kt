package com.stc.openweatherapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.stc.openweatherapp.R
import com.stc.openweatherapp.model.CityInfo
import com.stc.openweatherapp.model.DaySummaryResponse
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

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> get() = _weatherData

    private val _cityInfo = MutableLiveData<CityInfo>()
    val cityInfo: LiveData<CityInfo> get() = _cityInfo

    private val _locationError = MutableLiveData<String?>()
    val locationError: LiveData<String?> get() = _locationError

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading

    private val _daySummary = MutableLiveData<DaySummaryResponse>()
    val daySummary: LiveData<DaySummaryResponse> get() = _daySummary

    fun fetchCityCoordinates(cityName: String) {
        viewModelScope.launch {
            try {
                val result = weatherApiService.getCityCoordinates(cityName)
                if (result.isNotEmpty()) {
                    _cityInfo.postValue(
                        CityInfo(
                            lat = result[0].lat,
                            lon = result[0].lon,
                            name = result[0].name,
                            country = null,
                            local_names = null
                        )
                    )
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
                _cityInfo.postValue(result[0])
            } catch (e: Exception) {
                Timber.e(e, "Error fetching city info")
                _locationError.postValue("Error fetching city info")
            }
        }
    }

    fun fetchWeatherData() {
        val cityInfo = _cityInfo.value
        if (cityInfo != null) {
            fetchWeatherData(cityInfo.lat, cityInfo.lon)
        } else {
            Timber.e("Error fetching weather data")
            _locationError.postValue("Location not available")
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
        Timber.d("Location fetching")

        _loading.postValue(true) // Set loading to true when the request starts
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                Timber.d("Location fetched lat=${location.latitude} lon=${location.longitude}")
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

    fun fetchDaySummary(lat: Double, lon: Double, date: String) {
        _loading.postValue(true) // Set loading to true when the request starts
        viewModelScope.launch {
            try {
                val response = weatherApiService.getDaySummary(lat, lon, date)
                _daySummary.postValue(response)
                _locationError.postValue(null) // Clear error on success
            } catch (e: Exception) {
                val errorMessage = context.getString(R.string.error_fetching_day_summary)
                _locationError.postValue(errorMessage)
                Timber.e(e, "Error fetching day summary")
            } finally {
                _loading.postValue(false) // Set loading to false when the request ends
            }
        }
    }
}