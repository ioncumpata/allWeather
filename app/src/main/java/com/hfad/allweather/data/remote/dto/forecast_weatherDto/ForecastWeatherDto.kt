package com.hfad.allweather.data.remote.dto.forecast_weatherDto

import com.hfad.allweather.domain.model.forecast_weather.ForecastWeather

data class ForecastWeatherDto(
    val forecastday: List<ForecastdayDto>
)

fun ForecastWeatherDto.toForecastWeather(): ForecastWeather {
    return ForecastWeather(
        forecastday = forecastday.map { it.toForecastday() }
    )
}