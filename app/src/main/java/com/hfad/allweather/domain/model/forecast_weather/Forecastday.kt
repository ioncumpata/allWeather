package com.hfad.allweather.domain.model.forecast_weather

import com.hfad.allweather.data.remote.dto.forecast_weatherDto.DayForecastWeatherDto

data class Forecastday(
    val date: String,
    val day: DayForecastWeather,
)
