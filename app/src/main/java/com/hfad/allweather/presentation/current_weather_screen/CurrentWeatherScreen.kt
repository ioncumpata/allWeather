package com.hfad.allweather.presentation.current_weather_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hfad.allweather.R
import com.hfad.allweather.databinding.FragmentCurrentWeatherScreenBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CurrentWeatherScreen : Fragment() {

    private lateinit var binding: FragmentCurrentWeatherScreenBinding
    private val viewModel: CurrentWeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCurrentWeatherScreenBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        CoroutineScope(Dispatchers.IO).launch {
            viewModel.state.collectLatest { value ->

                withContext(Dispatchers.Main) {

                    if (value.isLoading) {

                    } else {
                        if (value.isError.isNotBlank()) {
                            Toast.makeText(requireContext(), value.isError, Toast.LENGTH_LONG)
                                .show()
                        } else {

                          updateCurrentView(value)
                        }


                    }
                }
            }

        }


    }

    private fun updateCurrentView(value: CurrentWeatherListState) = with(binding) {
        tvCity.text =  value.currentWeather?.location?.name.toString()
        tvCountry.text = value.currentWeather?.location?.country.toString()
        Picasso.get().load( value.currentWeather?.current?.condition?.icon).into(iconCondition)
        textCondition.text = value.currentWeather?.current?.condition?.text.toString()
        tvTemp.text = getString(R.string.temperature_format,value.currentWeather?.current?.temp_c.toString())
        tvFellsLike.text = getString(R.string.feels_like_format, value.currentWeather?.current?.feelslike_c.toString())
        tvSpeedWind.text = getString(R.string.wind_speed_format, value.currentWeather?.current?.wind_kph.toString())
        tvHumidity.text = getString(R.string.humidity_format, value.currentWeather?.current?.humidity.toString())

    }

    companion object {
        @JvmStatic
        fun newInstance() = CurrentWeatherScreen()
    }
}
