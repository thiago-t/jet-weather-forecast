package com.example.jetweatherforecast.model

data class Weather(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherJson>,
    val message: Double
)