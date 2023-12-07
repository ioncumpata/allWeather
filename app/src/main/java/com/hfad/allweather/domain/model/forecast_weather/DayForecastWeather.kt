package com.hfad.allweather.domain.model.forecast_weather

data class DayForecastWeather(
    val condition: ConditionForecastWeather,
    val averageTemp: Double,
)