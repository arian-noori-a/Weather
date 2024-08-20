package com.example.weather.view



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import kotlinx.coroutines.delay
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.runtime.collectAsState
import com.example.weather.model.CityData
import com.example.weather.viewModel.WeatherViewModel
import kotlin.toString



@Composable
fun WeatherScreen(navController: NavController, apikey: String) {

    val cities = CityData.cities
    val viewModel: WeatherViewModel = viewModel {
        WeatherViewModel(apiKey = apikey)
    }

    val weatherList by viewModel.weatherList.collectAsState()
    val error by viewModel.error.collectAsState()
    var cityName by remember {
        mutableStateOf("")
    }
    var showError by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        for(i in 0..cities.size - 1)
            viewModel.fetchWeather(cities[i])
    }

    LaunchedEffect(error) {
        if (error != null) {
            showError = true
            delay(3000)
            showError = false
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.padding(6.dp)) {

            Text(
                text = "Enter the city you want to see its information:",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = cityName,
                onValueChange = { cityName = it },
                label = { Text("Enter A City Name") },
                modifier = Modifier.fillMaxWidth(0.95f)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        viewModel.fetchWeather(cityName)
                        cityName = ""
                    }
                ) {
                    Text("Get Weather")
                }

                Button(onClick = {
                    navController.navigate("SavedMenu")
                }
                ) {
                    Text("See Saved Weathers")
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            LazyColumn {

                items(weatherList) { weather ->
                    WeatherDataView(weather)
                    Spacer(modifier = Modifier.height(6.dp))
                    Divider(color = Color.Gray)
                    Spacer(modifier = Modifier.height(6.dp))
                }

            }
        }

        AnimatedVisibility(
            visible = showError,
            enter = fadeIn(), exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.Center)
                .background(Color.Red)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .padding(6.dp)
            ) {
                Text(
                    text = error.toString(),
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

    }
}