package com.hfad.allweather.data.remote.dto.forecast_weatherDto

import com.hfad.allweather.domain.model.forecast_weather.ConditionForecastWeather

data class ConditionForecastWeatherDto(
    val code: Int,
    val icon: String,
    val text: String
)

fun ConditionForecastWeatherDto.toConditionForecastWeather(): ConditionForecastWeather{
    return ConditionForecastWeather(
        icon = icon,
        text = text
    )
}