
package com.example.weather


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
import kotlinx.coroutines.delay
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController




@Composable
fun WeatherScreen(navController: NavController) {
    val viewModel: WeatherViewModel = viewModel {
        WeatherViewModel(apiKey = "4e6b57fbb69ef616ce47bd9a4e88686f")
    }

    val weatherList by viewModel.weatherList.collectAsState()
    val error by viewModel.error.collectAsState()
    var cityName by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    LaunchedEffect(error) {
        if (error != null) {
            showError = true
            delay(1000)
            showError = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(26.dp)) {
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
                Button(onClick = { viewModel.fetchWeather(cityName) }) {
                    Text("Get Weather")
                }
                Button(onClick = { navController.navigate("savedWeathers") }) {
                    Text("See Saved Weathers")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(weatherList) { weather ->
                    WeatherDataView(weather)
                    Spacer(modifier = Modifier.height(16.dp))
                    Divider(color = Color.Gray, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(16.dp))
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
                    text = error ?: "",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}


@Composable
fun WeatherDataView(weather: WeatherResponse) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(text = "City: ${weather.name}", color = Color.White)
        Text(text = "Country: ${weather.sys.country}", color = Color.White)
        Text(
            text = "Coordinates: Lon ${weather.coord.lon}, Lat ${weather.coord.lat}",
            color = Color.White
        )
        Text(
            text = "Weather: ${weather.weather.joinToString { "${it.main}: ${it.description}" }}",
            color = Color.White
        )
        Text(text = "Temperature: ${weather.main.temp}°C", color = Color.White)
        Text(text = "Feels Like: ${weather.main.feels_like}°C", color = Color.White)
        Text(text = "Min Temperature: ${weather.main.temp_min}°C", color = Color.White)
        Text(text = "Max Temperature: ${weather.main.temp_max}°C", color = Color.White)
        Text(text = "Pressure: ${weather.main.pressure} hPa", color = Color.White)
        Text(text = "Humidity: ${weather.main.humidity}%", color = Color.White)
        Text(text = "Sea Level: ${weather.main.sea_level} hPa", color = Color.White)
        Text(text = "Ground Level: ${weather.main.grnd_level} hPa", color = Color.White)
        Text(text = "Visibility: ${weather.visibility} meters", color = Color.White)
        Text(text = "Wind Speed: ${weather.wind.speed} m/s", color = Color.White)
        Text(text = "Wind Direction: ${weather.wind.deg}°", color = Color.White)
        Text(text = "Cloud Coverage: ${weather.clouds.all}%", color = Color.White)
        Text(text = "Data Time: ${weather.dt}", color = Color.White)
        Text(text = "Timezone Offset: ${weather.timezone} seconds from UTC", color = Color.White)
        Text(text = "City ID: ${weather.id}", color = Color.White)
        Button(onClick = {}, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Save Weather")
        }
    }
}


@Composable
fun SavedWeathersScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }

        Spacer(modifier = Modifier.height(16.dp))


        Text("This is where saved weathers will be shown", color = Color.White)
    }
}


@Composable
fun WeatherAppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "weatherScreen") {
        composable("weatherScreen") { WeatherScreen(navController) }
        composable("savedWeathers") { SavedWeathersScreen(navController) }
    }
}

