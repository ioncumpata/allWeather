package com.hfad.allweather.data.remote.dto.forecast_weatherDto

import com.hfad.allweather.domain.model.forecast_weather.DayForecastWeather

data class DayForecastWeatherDto(
    val avghumidity: Int,
    val avgtemp_c: Double,
    val avgtemp_f: Double,
    val avgvis_km: Double,
    val avgvis_miles: Int,
    val condition: ConditionForecastWeatherDto,
    val daily_chance_of_rain: Int,
    val daily_chance_of_snow: Int,
    val daily_will_it_rain: Int,
    val daily_will_it_snow: Int,
    val maxtemp_c: Double,
    val maxtemp_f: Double,
    val maxwind_kph: Double,
    val maxwind_mph: Double,
    val mintemp_c: Double,
    val mintemp_f: Double,
    val totalprecip_in: Double,
    val totalprecip_mm: Double,
    val totalsnow_cm: Double,
    val uv: Int
)

fun DayForecastWeatherDto.toDayForecastWeather(): DayForecastWeather {
    return DayForecastWeather(
        condition = condition.toConditionForecastWeather(),
        averageTemp = avgtemp_c,
    )
}