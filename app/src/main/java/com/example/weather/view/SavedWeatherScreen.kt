package com.example.weather.view


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.weather.model.CityData

@Composable
fun SavedWeathersScreen(navController: NavController) {
    val savedWeathers = CityData.savedWeathers
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

