package com.hfad.allweather.data.remote.dto.forecast_weatherDto

import com.hfad.allweather.domain.model.forecast_weather.LocationForecastWeather

data class LocationForecastWeatherDto(
    val country: String,
    val lat: Double,
    val localtime: String,
    val localtime_epoch: Int,
    val lon: Double,
    val name: String,
    val region: String,
    val tz_id: String
)

fun LocationForecastWeatherDto.toLocationForecastWeather(): LocationForecastWeather{
    return LocationForecastWeather(
        region = region,
        country = country,
        name = name
    )
}