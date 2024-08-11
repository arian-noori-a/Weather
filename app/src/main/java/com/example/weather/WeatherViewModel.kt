package com.example.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val apiKey: String) : ViewModel() {

    // MutableStateFlow holding the list of weather responses. Initialized to an empty list.
    private val _weatherList = MutableStateFlow<List<WeatherResponse>>(emptyList())

    // StateFlow that provides read-only access to the weather list.
    val weatherList: StateFlow<List<WeatherResponse>> = _weatherList

    // MutableStateFlow holding error messages. Initialized to null indicating no error.
    private val _error = MutableStateFlow<String?>(null)

    // StateFlow that provides read-only access to error messages.
    val error: StateFlow<String?> = _error

    // Function to fetch weather data for a specified city and update the weather list and error state.
    fun fetchWeather(cityName: String) {
        viewModelScope.launch {
            try {
                // Fetch the current weather data from the API and update the weather list.
                val newWeather = ApiClient.getCurrentWeather(cityName, apiKey)
                _weatherList.value = listOf(newWeather) + _weatherList.value
                // Clear any existing error message.
                _error.value = null
            } catch (e: Exception) {
                // Set an error message if the fetch fails.
                _error.value = "Error fetching weather: ${e.message}.\n" +
                        "Check your connection and your city name."
            }
        }
    }
}
