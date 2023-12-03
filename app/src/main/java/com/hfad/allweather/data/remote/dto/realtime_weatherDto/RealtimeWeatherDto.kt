package com.hfad.allweather.data.remote.dto.realtime_weatherDto

import com.hfad.allweather.domain.model.realtime_weather.RealtimeWeather

data class RealtimeWeatherDto(
    val current: CurrentWeatherDto,
    val location: LocationWeatherDto
)

fun RealtimeWeatherDto.toRealtime(): RealtimeWeather{
    return RealtimeWeather(
        current = current.toCurrent(),
        location = location.toLocation()
    )
}