package com.hfad.allweather.domain.repository

import com.hfad.allweather.data.remote.dto.forecast_weatherDto.MainForecastWeatherDto
import com.hfad.allweather.data.remote.dto.realtime_weatherDto.RealtimeWeatherDto

interface WeatherRepository {

   suspend fun getForecastWeather(nameCity: String): MainForecastWeatherDto

   suspend fun getCurrentWeather(coordinates: String): RealtimeWeatherDto
}