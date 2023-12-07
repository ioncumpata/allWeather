package com.hfad.allweather.data.remote.dto.forecast_weatherDto

import com.hfad.allweather.domain.model.forecast_weather.CurrentForecastWeather

data class CurrentForecastWeatherDto(
    val cloud: Int,
    val condition: ConditionForecastWeatherDto,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val gust_kph: Double,
    val gust_mph: Double,
    val humidity: Int,
    val is_day: Int,
    val last_updated: String,
    val last_updated_epoch: Int,
    val precip_in: Double,
    val precip_mm: Double,
    val pressure_in: Double,
    val pressure_mb: Int,
    val temp_c: Double,
    val temp_f: Double,
    val uv: Int,
    val vis_km: Double,
    val vis_miles: Int,
    val wind_degree: Int,
    val wind_dir: String,
    val wind_kph: Double,
    val wind_mph: Double
)

fun CurrentForecastWeatherDto.toCurrentForecastWeather(): CurrentForecastWeather {
    return CurrentForecastWeather(
        condition = condition.toConditionForecastWeather(),
        temp_c = temp_c,
        humidity = humidity,
        wind_kph = wind_kph

    )

}