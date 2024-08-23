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
import com.example.weather.ui.theme.LocalColoring
import com.example.weather.ui.theme.LocalSpacing
import kotlin.collections.joinToString
import kotlin.text.trimIndent

@Composable
fun WeatherDataView(weather: WeatherResponse) {
    val savedWeathers = CityData.savedWeathers

    val minTemperature = weather.main.temp_min - 273
    val maxTemperature = weather.main.temp_max - 273
    val feelsLikeTemperature = weather.main.feels_like - 273
    val temperature = weather.main.temp - 273

    val weatherInfo = """
        City: ${weather.name}
        Temperature: ${"%.2f".format(temperature)}°C
        Country: ${weather.sys.country}
        Coordinates: Lon ${weather.coord.lon}, Lat ${weather.coord.lat}
        Weather: ${weather.weather.joinToString { "${it.main}: ${it.description}" }}
        Pressure: ${weather.main.pressure} hPa
        Humidity: ${weather.main.humidity}%
        Wind Speed: ${weather.wind.speed} m/s
    """.trimIndent()

    val moreInfo = """
        Coordinates: Lon ${weather.coord.lon}, Lat ${weather.coord.lat}
        Feels Like: ${"%.2f".format(feelsLikeTemperature)}°C
        Min Temperature: ${"%.2f".format(minTemperature)}°C
        Max Temperature: ${"%.2f".format(maxTemperature)}°C
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
            .border(width = LocalSpacing.current.extraSmall, color = Color.Gray)
            .padding(LocalSpacing.current.small)
    ) {
        Text(
            text = displayedText,
            color = LocalColoring.current.ColourFull.textColor
        )
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Button(onClick = {
                showMore = !showMore
            }) {
                Text("Show More")
            }
            Spacer(modifier = Modifier.width(LocalSpacing.current.small))
            Button(onClick = { savedWeathers.add(0, weatherInfo) }) {
                Text("Save Weather")
            }
        }
    }
}

