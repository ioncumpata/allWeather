package com.hfad.allweather.domain.model.forecast_weather

import com.hfad.allweather.data.remote.dto.forecast_weatherDto.ForecastdayDto

data class ForecastWeather(
    val forecastday: List<Forecastday>
)
