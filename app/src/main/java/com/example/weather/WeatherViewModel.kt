package com.example.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val apiKey: String) : ViewModel() {

    private val _weatherList = MutableStateFlow<List<WeatherResponse>>(emptyList())

    val weatherList: StateFlow<List<WeatherResponse>> = _weatherList

    private val _error = MutableStateFlow<String?>(null)

    val error: StateFlow<String?> = _error

    fun fetchWeather(cityName: String) {
        viewModelScope.launch {
            try {
                val newWeather = ApiClient.getCurrentWeather(cityName, apiKey)
                _weatherList.value = listOf(newWeather) + _weatherList.value

                _error.value = null
            } catch (e: Exception) {

                _error.value = "Error fetching weather: ${e.message}.\n" +
                        "Check your connection and your city name."
            }
        }
    }
}
