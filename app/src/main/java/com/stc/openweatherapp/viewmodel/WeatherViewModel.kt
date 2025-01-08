package com.stc.openweatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stc.openweatherapp.model.CityCoordinates
import com.stc.openweatherapp.model.CityInfo
import com.stc.openweatherapp.model.WeatherResponse
import com.stc.openweatherapp.repository.WeatherApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherApiService: WeatherApiService
) : ViewModel() {


    private val _coordinates = MutableLiveData<List<CityCoordinates>>()
    val coordinates: LiveData<List<CityCoordinates>> get() = _coordinates

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> get() = _weatherData

    private val _cityInfo = MutableLiveData<List<CityInfo>>()
    val cityInfo: LiveData<List<CityInfo>> get() = _cityInfo

    fun fetchCityCoordinates(cityName: String) {
        viewModelScope.launch {
            try {
                val result = weatherApiService.getCityCoordinates(cityName)
                _coordinates.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
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
        viewModelScope.launch {
            try {
                val response = weatherApiService.getWeatherData(lat, lon, exclude)
                _weatherData.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}