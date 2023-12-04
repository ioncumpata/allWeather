package com.hfad.allweather.presentation.current_weather_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.allweather.common.Resource
import com.hfad.allweather.data.location.DefaultLocationTracker
import com.hfad.allweather.domain.location.LocationTracker
import com.hfad.allweather.domain.use_cases.GetCurrentWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val locationTracker: DefaultLocationTracker
) : ViewModel() {

    private val _state = MutableStateFlow(CurrentWeatherListState())
    val state: StateFlow<CurrentWeatherListState> = _state

    init {
        getCurrentWeather()
    }


    private fun getCurrentWeather() = viewModelScope.launch(Dispatchers.IO) {

        delay(500L)
        val coordinates = locationTracker.getLocation().toString()
        Log.d("Coordinates", coordinates)

       /* try {


            getCurrentWeatherUseCase(coordinates).collect { result ->
                when (result) {
                    is Resource.Success -> {

                        _state.value = CurrentWeatherListState(currentWeather = result.data)

                    }
                    is Resource.Error -> {
                        _state.value = CurrentWeatherListState(
                            isError = result.message ?: "An error occur"
                        )

                    }
                    is Resource.Loading -> {
                        _state.value = CurrentWeatherListState(isLoading = true)

                    }
                }
            }
        } catch (e: Exception) {
            _state.value = CurrentWeatherListState(
                isLoading = false,
                currentWeather = null,
                isError = "error fetching weather"
            )
            Log.e("ViewModel", "Error fetching weather: ${e.message}")
        }*/
    }


}