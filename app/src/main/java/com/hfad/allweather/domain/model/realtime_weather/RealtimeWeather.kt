package com.hfad.allweather.domain.model.realtime_weather

data class RealtimeWeather(
    val current: CurrentWeather,
    val location: LocationWeather
)
