package com.hfad.allweather.data.repository

import com.hfad.allweather.data.remote.WeatherAPI
import com.hfad.allweather.data.remote.dto.forecast_weatherDto.MainForecastWeatherDto
import com.hfad.allweather.data.remote.dto.realtime_weatherDto.RealtimeWeatherDto
import com.hfad.allweather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherAPI: WeatherAPI
): WeatherRepository {


    override suspend fun getForecastWeather(nameCity: String): MainForecastWeatherDto {

        return weatherAPI.getForecastWeather(nameCity)
    }

    override suspend fun getCurrentWeather(coordinates: String): RealtimeWeatherDto {

        return weatherAPI.getCurrentWeather(coordinates)

    }
}