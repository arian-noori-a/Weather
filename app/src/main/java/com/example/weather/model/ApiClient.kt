package com.example.weather.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.jvm.java

object ApiClient {

    private const val KEY = "4e6b57fbb69ef616ce47bd9a4e88686f"
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"


    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    suspend fun getCurrentWeather(
        cityName: String,
        apiKey: String
    ): WeatherResponse {
        return retrofit.create(ApiService::class.java)
            .getCurrentWeather(cityName, apiKey, "metrics")
    }


    private interface ApiService {
        @GET("weather")
        suspend fun getCurrentWeather(
            @Query("q") cityName: String,
            @Query("appid") apiKey: String,
            @Query("units") units: String
        ): WeatherResponse
    }

    fun getKey(): String {
        return KEY
    }

}
