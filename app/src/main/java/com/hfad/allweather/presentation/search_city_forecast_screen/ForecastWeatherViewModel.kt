package com.hfad.allweather.presentation.search_city_forecast_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.allweather.common.Resource
import com.hfad.allweather.domain.use_cases.GetForecastWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastWeatherViewModel @Inject constructor(
    private val getForecastWeatherUseCase: GetForecastWeatherUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ForecastWeatherListState())
    val state: StateFlow<ForecastWeatherListState> = _state


    fun getForecastWeather(nameCity: String) = viewModelScope.launch(Dispatchers.IO) {

        delay(500L)


        getForecastWeatherUseCase(nameCity).collect { result ->
            when (result) {
                is Resource.Success -> {

                    _state.value = ForecastWeatherListState(forecastWeather = result.data)

                }
                is Resource.Error -> {
                    _state.value = ForecastWeatherListState(
                        isError = result.message ?: "An error occur"
                    )

                }
                is Resource.Loading -> {
                    _state.value = ForecastWeatherListState(isLoading = true)

                }
            }
        }


    }

}