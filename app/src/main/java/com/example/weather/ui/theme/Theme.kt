package com.example.weather.ui.theme


import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color


data class Theme(
    val NightMode: Colors =  Colors(Color.White , Color.DarkGray),
    val LightMode: Colors = Colors(Color.Black , Color.White),
    val ColourFull: Colors = Colors(Color.Yellow , Color.Red)
)

val LocalColoring = compositionLocalOf {
    Theme()
}

class Colors(
    val textColor: Color,
    val backgroundColor: Color
)