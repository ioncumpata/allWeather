package com.hfad.allweather.data.remote.dto.forecast_weatherDto

import com.hfad.allweather.domain.model.forecast_weather.MainForecastWeather

data class MainForecastWeatherDto(
    val current: CurrentForecastWeatherDto,
    val forecast: ForecastWeatherDto,
    val location: LocationForecastWeatherDto
)

fun MainForecastWeatherDto.toMainMainForecastWeather(): MainForecastWeather{
    return MainForecastWeather(
        forecast = forecast,
        location = location,
        current = current
    )
}