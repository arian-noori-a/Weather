package com.example.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

//The MainActivity class:
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //The personal key of the OpenWeatherMap website:
        val apiKey = "4e6b57fbb69ef616ce47bd9a4e88686f"

        setContent {

            Box(modifier = Modifier.fillMaxSize().background(Color.DarkGray))
            {
                val viewModel: WeatherViewModel = viewModel {
                    WeatherViewModel(apiKey)
                }
                WeatherScreen(viewModel)
            }
        }
    }
}
