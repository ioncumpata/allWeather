package com.hfad.allweather.domain.model.forecast_weather

data class Forecastday(
    val date: String,
    val day: DayForecastWeather,
    val hour: List<HourForecastWeather>
)
