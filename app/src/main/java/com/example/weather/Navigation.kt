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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import kotlinx.coroutines.delay
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.runtime.Composable


import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.nio.file.WatchEvent


// The main composable function in which we save our menus:
@Composable
fun WeatherAppNavGraph(apikey: String) {
    // to save our navigation state:
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "MainMenu") {

        composable("MainMenu") { WeatherScreen(navController, apikey) }
        composable("SavedMenu") { SavedWeathersScreen(navController) }

    }
}



@Composable
fun WeatherScreen(navController: NavController, apikey: String) {

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

    // When the composable is run, this block will get the weathers of
    // the default cities which are in the "cities".
    LaunchedEffect(Unit) {
        for(i in 0..cities.size - 1)
        viewModel.fetchWeather(cities[i])
    }

    // When the error from the weather view is not null this block will be
    // executed for 3 seconds.
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

@Composable
fun WeatherDataView(weather: WeatherResponse) {
    val weatherInfo = """
        City: ${weather.name}
        Country: ${weather.sys.country}
        Coordinates: Lon ${weather.coord.lon}, Lat ${weather.coord.lat}
        Weather: ${weather.weather.joinToString { "${it.main}: ${it.description}" }}
        Pressure: ${weather.main.pressure} hPa
        Humidity: ${weather.main.humidity}%
        Wind Speed: ${weather.wind.speed} m/s
    """.trimIndent()

    val moreInfo = """
        Coordinates: Lon ${weather.coord.lon}, Lat ${weather.coord.lat}
        Feels Like: ${weather.main.feels_like}°C
        Min Temperature: ${weather.main.temp_min}°C
        Max Temperature: ${weather.main.temp_max}°C
        Sea Level: ${weather.main.sea_level} hPa
        Ground Level: ${weather.main.grnd_level} hPa
        Visibility: ${weather.visibility} meters
        Wind Direction: ${weather.wind.deg}°
        Cloud Coverage: ${weather.clouds.all}%
        Data Time: ${weather.dt}
        Timezone Offset: ${weather.timezone} seconds from UTC
        City ID: ${weather.id}
    """.trimIndent()


    var showMore by remember { mutableStateOf(false) }

    val displayedText = if (showMore) {
        "$weatherInfo\n\n$moreInfo"
    } else {
        weatherInfo
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 2.dp, color = Color.Gray)
            .padding(6.dp)
    ) {
        Text(
            text = displayedText,
            color = Color.White
        )
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Button(onClick = {
                showMore = !showMore
            }) {
                Text("Show More")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { savedWeathers.add(0, weatherInfo) }) {
                Text("Save Weather")
            }
        }
    }
}

@Composable
fun SavedWeathersScreen(navController: NavController) {

    LazyColumn(modifier = Modifier.fillMaxSize().padding(6.dp)) {
        item {
            Button(onClick = { navController.navigate("MainMenu") }) {
                Text("Back")
            }
            Spacer(modifier = Modifier.height(6.dp))
        }

        items(savedWeathers.size) { index ->
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 2.dp, color = Color.Gray)
                    .padding(6.dp)
            ) {

                Text(text = savedWeathers[index], color = Color.White)
                Button(onClick = {
                    savedWeathers.removeAt(index)
                }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text(text = "Remove Weather")
                }
                Spacer(modifier = Modifier.height(6.dp))
            }
            Spacer(modifier = Modifier.height(6.dp))
            Divider(color = Color.Gray)
        }
    }

}


// List of the default cities where their weather is on the main menu:
val cities = listOf(
    "Washington",          // United States
    "Beijing",             // China
    "New Delhi",           // India
    "Moscow",              // Russia
    "Tokyo",               // Japan
    "Berlin",              // Germany
    "Paris",               // France
    "London",              // United Kingdom
    "Ottawa",              // Canada
    "Canberra",            // Australia
    "Brasília",            // Brazil
    "Rom",                 // Italy
    "Seoul",               // South Korea
    "Riyadh",              // Saudi Arabia
    "Mexico City",         // Mexico
    "Jakarta",             // Indonesia
    "Ankara",              // Turkey
    "Buenos Aires",        // Argentina
    "Madrid",              // Spain
    "Amsterdam",           // Netherlands
    "Bern",                // Switzerland
    "Stockholm",           // Sweden
    "Brussels",            // Belgium
    "Oslo",                // Norway
    "Vienna",              // Austria
    "Copenhagen",          // Denmark
    "Helsinki",            // Finland
    "Athens",              // Greece
    "Cairo",               // Egypt
    "Jerusalem",           // Israel
    "Abu Dhabi",           // United Arab Emirates
    "Doha",                // Qatar
    "Singapore",           // Singapore
    "Kuala Lumpur",        // Malaysia
    "Manila",              // Philippines
    "Hanoi",               // Vietnam
    "Bangkok",             // Thailand
    "Islamabad",           // Pakistan
    "Dhaka",               // Bangladesh
    "Warsaw",              // Poland
    "Kyiv",                // Ukraine
    "Bogotá",              // Colombia
    "Santiago",            // Chile
    "Lima",                // Peru
    "Rabat",               // Morocco
    "Tunis",               // Tunisia
    "Abuja",               // Nigeria
    "Nairobi",             // Kenya
    "Accra",               // Ghana
    "Tehran",              // Iran
    "Baghdad",             // Iraq
    "Beirut",              // Lebanon
    "Amman",               // Jordan
    "Muscat",              // Oman
    "Kuwait",              // Kuwait
    "Minsk",               // Belarus
    "Vilnius",             // Lithuania
    "Ljubljana",           // Slovenia
    "Zagreb",              // Croatia
    "Belgrade",            // Serbia
    "Sarajevo",            // Bosnia and Herzegovina
    "Tirana",              // Albania
    "Luxembourg",          // Luxembourg
    "Hong Kong",           // Hong Kong
    "Yerevan",             // Armenia
    "Tbilisi",             // Georgia
    "Baku",                // Azerbaijan
    "Astana",              // Kazakhstan
    "Tashkent",            // Uzbekistan
    "Ashgabat",            // Turkmenistan
    "Bishkek",             // Kyrgyzstan
    "Kathmandu",           // Nepal
    "Colombo",             // Sri Lanka
    "Naypyidaw",           // Myanmar
    "Wellington",          // New Zealand
)

// List of the weathers in the save menu:
val savedWeathers = mutableListOf<String>()