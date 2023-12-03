package com.hfad.allweather.domain.use_cases

import com.hfad.allweather.common.Resource
import com.hfad.allweather.data.remote.dto.realtime_weatherDto.toRealtime
import com.hfad.allweather.domain.model.realtime_weather.RealtimeWeather
import com.hfad.allweather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    val weatherRepository: WeatherRepository
) {

    operator fun invoke(coordinates: String): Flow<Resource<RealtimeWeather>> = flow {

        try {

            emit(Resource.Loading())
            val currentWeather = weatherRepository.getCurrentWeather(coordinates).toRealtime()
            emit(Resource.Success(currentWeather))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))


        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))


        }
    }

}