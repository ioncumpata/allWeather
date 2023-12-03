package com.hfad.allweather.presentation.current_weather_screen

import com.hfad.allweather.domain.model.realtime_weather.RealtimeWeather

data class CurrentWeatherListState(
    val isLoading: Boolean = false,
    val currentWeather: RealtimeWeather? = null ,
    val isError: String = ""
)
