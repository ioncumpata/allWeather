package com.hfad.allweather.presentation.search_city_forecast_screen

import com.hfad.allweather.domain.model.forecast_weather.MainForecastWeather

data class ForecastWeatherListState(
    val isLoading: Boolean = false,
    val forecastWeather: MainForecastWeather? = null,
    val isError: String = ""
)