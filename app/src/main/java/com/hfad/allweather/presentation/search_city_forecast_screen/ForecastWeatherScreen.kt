package com.hfad.allweather.presentation.search_city_forecast_screen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.allweather.R
import com.hfad.allweather.databinding.FragmentForecastWeatherScreenBinding
import com.hfad.allweather.presentation.current_weather_screen.CurrentWeatherListState
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@AndroidEntryPoint
class ForecastWeatherScreen : Fragment() {

    private lateinit var binding: FragmentForecastWeatherScreenBinding
    private val viewModel: ForecastWeatherViewModel by viewModels()
    private lateinit var adapter: ForecastWeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForecastWeatherScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ForecastWeatherAdapter()
        val layoutManager = LinearLayoutManager(context)
        binding.rcView.layoutManager = layoutManager
        binding.rcView.adapter = adapter




        binding.searchCity.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(dayWeather: String?): Boolean {
                if (dayWeather != null) {
                    viewModel.getForecastWeather(dayWeather)
                    binding.cardForecast.visibility = View.VISIBLE
                    binding.cardRecycler.visibility = View.VISIBLE
                }
                return true
            }

            override fun onQueryTextChange(dayWeather: String?): Boolean {
                return true
            }

        })
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewModel.state.collectLatest { value ->

                withContext(Dispatchers.Main) {

                    if (value.isLoading) {
                        binding.progressBar.visibility = View.VISIBLE

                    } else {

                        if (value.isError.isNotBlank()) {
                            Toast.makeText(requireContext(), value.isError, Toast.LENGTH_LONG)
                                .show()
                        } else {

                            adapter.items =
                                value.forecastWeather?.forecast?.forecastday?.get(0)?.hour ?: emptyList()
                            updateForecastCardView(value)
                            hideKeyboard(binding.searchCity)
                            binding.progressBar.visibility = View.GONE

                        }


                    }
                }
            }

        }


    }
    @SuppressLint("StringFormatInvalid", "StringFormatMatches")
    private fun updateForecastCardView(value: ForecastWeatherListState) =

        with(binding) {
            tvCity.text = value.forecastWeather?.location?.name
            tvCondition.text = value.forecastWeather?.current?.condition?.text
            tvTemp.text =  getString(
                R.string.temperature_format,
                value.forecastWeather?.current?.temp_c.toString()
            )
            Picasso.get().load("https:" +value.forecastWeather?.current?.condition?.icon).into(ivCondition)
        }

    private fun hideKeyboard(view: View) {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}