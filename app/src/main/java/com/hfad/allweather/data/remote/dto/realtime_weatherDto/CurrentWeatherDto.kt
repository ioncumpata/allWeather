package com.hfad.allweather.data.remote.dto.realtime_weatherDto

import com.hfad.allweather.domain.model.realtime_weather.CurrentWeather

data class CurrentWeatherDto(
    val cloud: Int,
    val condition: ConditionWeatherDto,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val gust_kph: Double,
    val gust_mph: Double,
    val humidity: Int,
    val is_day: Int,
    val last_updated: String,
    val last_updated_epoch: Int,
    val precip_in: Int,
    val precip_mm: Int,
    val pressure_in: Double,
    val pressure_mb: Int,
    val temp_c: Int,
    val temp_f: Double,
    val uv: Int,
    val vis_km: Double,
    val vis_miles: Int,
    val wind_degree: Int,
    val wind_dir: String,
    val wind_kph: Double,
    val wind_mph: Double
)

fun CurrentWeatherDto.toCurrent(): CurrentWeather{
    return CurrentWeather(
        condition = condition.toCondition(),
        feelslike_c = feelslike_c,
        humidity = humidity,
        temp_c = temp_c,
        wind_kph = wind_kph
    )
}