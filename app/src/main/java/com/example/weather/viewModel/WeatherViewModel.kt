package com.example.weather.viewModel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.model.WeatherResponse
import com.example.weather.model.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.collections.plus


class WeatherViewModel(private val apiKey: String) : ViewModel() {

    private val _weatherList = MutableStateFlow<List<WeatherResponse>>(emptyList())
    val weatherList: StateFlow<List<WeatherResponse>> = _weatherList


    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchWeather(cityName: String) {

        viewModelScope.launch {
            _error.value = null

            try {
                val newWeather = ApiClient.getCurrentWeather(cityName, apiKey)
                _weatherList.value = listOf(newWeather) + _weatherList.value
            } catch (e: Exception) {
                _error.value = "Error in fetching weather: ${e.message}.\n" +
                        "Check your connection and your city name."
            }
        }
    }
}
