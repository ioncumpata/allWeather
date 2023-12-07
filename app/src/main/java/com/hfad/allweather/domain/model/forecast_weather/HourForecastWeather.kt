package com.hfad.allweather.domain.model.forecast_weather

import com.hfad.allweather.data.remote.dto.forecast_weatherDto.ConditionForecastWeatherDto

data class HourForecastWeather(
    val temp_c: Double,
    val condition: ConditionForecastWeather,
    val time: String,
)
