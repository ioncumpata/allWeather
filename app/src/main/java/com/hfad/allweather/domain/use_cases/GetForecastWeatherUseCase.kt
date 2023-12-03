package com.hfad.allweather.domain.use_cases

import com.hfad.allweather.common.Resource
import com.hfad.allweather.data.remote.dto.forecast_weatherDto.toMainMainForecastWeather
import com.hfad.allweather.domain.model.forecast_weather.MainForecastWeather
import com.hfad.allweather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetForecastWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    operator fun invoke(nameCity: String): Flow<Resource<MainForecastWeather>> = flow {

        try {

            emit(Resource.Loading())
            val currentWeather =
                weatherRepository.getForecastWeather(nameCity).toMainMainForecastWeather()
            emit(Resource.Success(currentWeather))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))


        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))


        }
    }
}