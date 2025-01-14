package com.stc.openweatherapp.repository

import com.stc.openweatherapp.BuildConfig
import com.stc.openweatherapp.model.CityCoordinates
import com.stc.openweatherapp.model.CityInfo
import com.stc.openweatherapp.model.DaySummaryResponse
import com.stc.openweatherapp.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("geo/1.0/direct")
    suspend fun getCityCoordinates(
        @Query("q") cityName: String,
        @Query("limit") limit: Int = 1,
        @Query("appid") apiKey: String = BuildConfig.OPEN_WEATHER_API_KEY
    ): List<CityCoordinates>

    @GET("data/3.0/onecall")
    suspend fun getWeatherData(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") exclude: String? = null, // Optional parameter
        @Query("appid") apiKey: String = BuildConfig.OPEN_WEATHER_API_KEY,
        @Query("units") units: String = "metric"
    ): WeatherResponse

    @GET("geo/1.0/reverse")
    suspend fun getCityByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("limit") limit: Int = 1,
        @Query("appid") apiKey: String = BuildConfig.OPEN_WEATHER_API_KEY
    ): List<CityInfo>

    @GET("data/3.0/onecall/day_summary")
    suspend fun getDaySummary(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("date") date: String,
        @Query("appid") apiKey: String = BuildConfig.OPEN_WEATHER_API_KEY
    ): DaySummaryResponse
}