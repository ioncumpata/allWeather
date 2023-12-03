package com.hfad.allweather.data.remote.dto.realtime_weatherDto

import com.hfad.allweather.domain.model.realtime_weather.ConditionWeather

data class ConditionWeatherDto(
    val code: Int,
    val icon: String,
    val text: String
)

fun ConditionWeatherDto.toCondition(): ConditionWeather{
    return ConditionWeather(
        text = text,
        icon = icon
    )
}