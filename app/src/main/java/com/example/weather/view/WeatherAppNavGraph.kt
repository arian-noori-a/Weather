package com.example.weather.view



import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


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