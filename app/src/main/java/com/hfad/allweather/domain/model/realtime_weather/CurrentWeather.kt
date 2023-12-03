package com.hfad.allweather.domain.model.realtime_weather

data class CurrentWeather(
    val condition: ConditionWeather,
    val feelslike_c: Double,
    val humidity: Int,
    val temp_c: Int,
    val wind_kph: Double
    )
