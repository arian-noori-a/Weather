package com.example.weather.view




import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Alignment
import com.example.weather.model.CityData
import com.example.weather.model.WeatherResponse
import kotlin.collections.joinToString
import kotlin.text.trimIndent


@Composable
fun WeatherDataView(weather: WeatherResponse) {
    val savedWeathers = CityData.savedWeathers
    val weatherInfo = "City: ${weather.name}\n" +
            "Temperature: ${weather.main.temp - 273}°C\n" +
            "Country: ${weather.sys.country}\n" +
            "Coordinates: Lon ${weather.coord.lon}, Lat ${weather.coord.lat}\n" +
            "Weather: ${weather.weather.joinToString{"${it.main}: ${it.description}"}}\n" +
            "Pressure: ${weather.main.pressure} hPa\n" +
            "Humidity: ${weather.main.humidity}%\n" +
            "Wind Speed: ${weather.wind.speed} m/s"

    val moreInfo = """
        Coordinates: Lon ${weather.coord.lon}, Lat ${weather.coord.lat}
        Feels Like: ${weather.main.feels_like - 273}°C
        Min Temperature: ${weather.main.temp_min - 273}°C
        Max Temperature: ${weather.main.temp_max - 273}°C
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
        "$weatherInfo\n$moreInfo"
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
