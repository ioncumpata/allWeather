package com.hfad.allweather.presentation.current_weather_screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationTokenSource
import com.hfad.allweather.R
import com.hfad.allweather.common.Constants
import com.hfad.allweather.common.DialogManager
import com.hfad.allweather.common.PermissionUtils
import com.hfad.allweather.databinding.FragmentCurrentWeatherScreenBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CurrentWeatherScreen : Fragment() {


    private lateinit var binding: FragmentCurrentWeatherScreenBinding
    private val viewModel: CurrentWeatherViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var pLauncher: ActivityResultLauncher<String>
    private val coordinates = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        checkLocation(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrentWeatherScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coordinates.observe(viewLifecycleOwner) { newLocation ->
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {

                if (newLocation.isNullOrEmpty()) {
                    viewModel.getCurrentWeather(Constants.DEFAULT_LOCATION)
                } else {
                    viewModel.getCurrentWeather(newLocation)
                }
                viewModel.state.collectLatest { value ->

                    withContext(Dispatchers.Main) {

                        if (value.isLoading) {

                            binding.progressBar.visibility = View.VISIBLE

                        } else {
                            if (value.isError.isNotBlank()) {
                                Toast.makeText(requireContext(), value.isError, Toast.LENGTH_LONG)
                                    .show()

                            } else {
                                binding.progressBar.visibility = View.GONE
                                updateCurrentView(value)
                            }


                        }
                    }
                }

            }


        }
    }


    @SuppressLint("StringFormatInvalid", "StringFormatMatches")
    private fun updateCurrentView(value: CurrentWeatherListState) =

        with(binding) {

            tvCity.text = value.currentWeather?.location?.name.toString()
            tvCountry.text = value.currentWeather?.location?.country.toString()
            Picasso.get().load("https:" + value.currentWeather?.current?.condition?.icon)
                .into(iconCondition)
            textCondition.text = value.currentWeather?.current?.condition?.text.toString()
            tvTemp.text =
                getString(
                    R.string.temperature_format,
                    value.currentWeather?.current?.temp_c.toString()
                )
            tvFellsLike.text = getString(
                R.string.feels_like_format,
                value.currentWeather?.current?.feelslike_c.toString()
            )
            tvSpeedWind.text = getString(
                R.string.wind_speed_format,
                value.currentWeather?.current?.wind_kph.toString()
            )
            tvHumidity.text =
                getString(
                    R.string.humidity_format,
                    value.currentWeather?.current?.humidity.toString()
                )
            tvHourDate.text = value.currentWeather?.location?.localtime.toString()

            btUpdate.setOnClickListener {
                checkLocation(requireContext())
            }

        }

    override fun onResume() {
        super.onResume()
        checkLocation(requireContext())
    }


    private fun checkLocation(context: Context) {
        if (PermissionUtils.isLocationEnabled(context)) {
            getLocation(context)
        } else {
            DialogManager.locationSettingsDialog(context, object : DialogManager.Listener {
                override fun onClick() {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
            })
        }

    }

    @SuppressLint("MissingPermission")
    private fun getLocation(context: Context) {
        val ct = CancellationTokenSource()
        if (!PermissionUtils.isAccessFineLocationGranted(context)) {

            pLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) {
                Toast.makeText(context, "Permission is $it", Toast.LENGTH_LONG).show()
            }

            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {


            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, ct.token)
                .addOnCompleteListener {
                    coordinates.value = "${it.result.latitude},${it.result.longitude}"
                }
        }
    }


}
