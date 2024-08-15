package com.example.weather


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


class MainActivity : ComponentActivity() {

    companion object {
        // The key which you get from the data holder website:
        private const val API_KEY = "4e6b57fbb69ef616ce47bd9a4e88686f"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            Box(modifier = Modifier.fillMaxSize().background(Color.DarkGray)) {
                WeatherAppNavGraph(apikey = API_KEY)
            }

        }
    }
}
