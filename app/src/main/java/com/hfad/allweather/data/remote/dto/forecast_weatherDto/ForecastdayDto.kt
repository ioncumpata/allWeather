package com.hfad.allweather.data.remote.dto.forecast_weatherDto

import com.hfad.allweather.domain.model.forecast_weather.Forecastday

data class ForecastdayDto(
    val astro: AstroForecastWeatherDto,
    val date: String,
    val date_epoch: Int,
    val day: DayForecastWeatherDto,
    val hour: List<HourForecastWeatherDto>
)

fun ForecastdayDto.toForecastday(): Forecastday {
    return Forecastday(
        date = date,
        day = day.toDayForecastWeather(),

    )
}