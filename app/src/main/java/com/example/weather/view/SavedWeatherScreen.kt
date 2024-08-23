package com.example.weather.view


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.weather.model.CityData
import com.example.weather.ui.theme.LocalColoring
import com.example.weather.ui.theme.LocalSpacing

@Composable
fun SavedWeathersScreen(navController: NavController) {
    val savedWeathers = CityData.savedWeathers
    LazyColumn(modifier = Modifier.fillMaxSize().padding(LocalSpacing.current.small)) {
        item {
            Button(onClick = { navController.navigate("MainMenu") }) {
                Text("Back")
            }
            Spacer(modifier = Modifier.height(LocalSpacing.current.small))
        }

        items(savedWeathers.size) { index ->
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = LocalSpacing.current.extraSmall, color = Color.Gray)
                    .padding(LocalSpacing.current.small)
            ) {

                Text(text = savedWeathers[index],
                    color = LocalColoring.current.ColourFull.textColor)
                Button(onClick = {
                    savedWeathers.removeAt(index)
                }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text(text = "Remove Weather")
                }
                Spacer(modifier = Modifier.height(LocalSpacing.current.small))
            }
            Spacer(modifier = Modifier.height(LocalSpacing.current.small))
            Divider(color = Color.Gray)
        }
    }

}

