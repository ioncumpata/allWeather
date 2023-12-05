package com.hfad.allweather.data.remote

import com.hfad.allweather.data.remote.dto.forecast_weatherDto.MainForecastWeatherDto
import com.hfad.allweather.data.remote.dto.realtime_weatherDto.RealtimeWeatherDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherAPI {

    @Headers(
        "X-RapidAPI-Key: f3e937b351msh7645c07e5ecaa81p1ec214jsn4eaa57bdb635",
        "X-RapidAPI-Host: weatherapi-com.p.rapidapi.com"
    )
    @GET("current.json")
    suspend fun getCurrentWeather(@Query("q") coordinates: String): RealtimeWeatherDto


    @Headers(
        "X-RapidAPI-Key: f3e937b351msh7645c07e5ecaa81p1ec214jsn4eaa57bdb635",
        "X-RapidAPI-Host: weatherapi-com.p.rapidapi.com"
    )
    @GET("forecast.json")
    suspend fun getForecastWeather(@Query("q") nameCity: String): MainForecastWeatherDto

}