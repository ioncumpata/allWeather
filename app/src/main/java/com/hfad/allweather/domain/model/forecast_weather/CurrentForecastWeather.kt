package com.hfad.allweather.domain.model.forecast_weather

data class CurrentForecastWeather(
    val condition: ConditionForecastWeather,
    val temp_c: Double,
    val humidity: Int,
    val wind_kph: Double
)
