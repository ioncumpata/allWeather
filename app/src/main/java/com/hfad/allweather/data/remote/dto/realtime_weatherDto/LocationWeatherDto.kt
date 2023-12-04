package com.hfad.allweather.data.remote.dto.realtime_weatherDto

import com.hfad.allweather.domain.model.realtime_weather.LocationWeather

data class LocationWeatherDto(
    val country: String,
    val lat: Double,
    val localtime: String,
    val localtime_epoch: Int,
    val lon: Double,
    val name: String,
    val region: String,
    val tz_id: String
)

fun LocationWeatherDto.toLocation(): LocationWeather{
    return LocationWeather(
        name = name,
        country = country,
        localtime = localtime
    )
}