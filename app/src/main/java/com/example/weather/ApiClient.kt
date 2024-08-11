package com.example.weather

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object ApiClient {

    // The URL of the weather website which we will fetch our data:
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    // The Retrofit instance used to create API service implementations:
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Function to get the current weather information for a specified city:
    suspend fun getCurrentWeather(
        cityName: String,
        apiKey: String,
        units: String = "metric"
    ): WeatherResponse {
        return retrofit.create(ApiService::class.java)
            .getCurrentWeather(cityName, apiKey, units)
    }

    // Interface defining the API endpoints and their parameters:
    private interface ApiService {
        @GET("weather")
        suspend fun getCurrentWeather(
            @Query("q") cityName: String,
            @Query("appid") apiKey: String,
            @Query("units") units: String
        ): WeatherResponse
    }
}
