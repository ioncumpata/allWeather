package com.hfad.allweather.domain.model.forecast_weather

import com.hfad.allweather.data.remote.dto.forecast_weatherDto.CurrentForecastWeatherDto
import com.hfad.allweather.data.remote.dto.forecast_weatherDto.ForecastWeatherDto
import com.hfad.allweather.data.remote.dto.forecast_weatherDto.LocationForecastWeatherDto

data class MainForecastWeather(
    val current: CurrentForecastWeatherDto,
    val forecast: ForecastWeatherDto,
    val location: LocationForecastWeatherDto
)
