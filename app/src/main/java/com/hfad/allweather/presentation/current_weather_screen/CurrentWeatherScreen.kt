package com.hfad.allweather.presentation.current_weather_screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hfad.allweather.R
import com.hfad.allweather.common.PermissionUtils
import com.hfad.allweather.databinding.FragmentCurrentWeatherScreenBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

private const val LOCATION_PERMISSION_REQUEST_CODE = 34

@AndroidEntryPoint
class CurrentWeatherScreen : Fragment() {


    private lateinit var binding: FragmentCurrentWeatherScreenBinding
    private val viewModel: CurrentWeatherViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var coordinates: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCurrentWeatherScreenBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())


        CoroutineScope(Dispatchers.IO).launch {
            while (coordinates.isEmpty() || !isNetworkAvailable()) {
                delay(100) // Adjust the delay as needed
            }

            viewModel.getCurrentWeather(coordinates)

            viewModel.state.collectLatest { value ->

                withContext(Dispatchers.Main) {

                    if (value.isLoading) {

                    } else {
                        if (value.isError.isNotBlank()) {
                            Toast.makeText(requireContext(), value.isError, Toast.LENGTH_LONG)
                                .show()
                            Log.d("Http", value.isError)

                        } else {

                            updateCurrentView(value)
                        }


                    }
                }
            }

        }


    }


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

        }


    override fun onStart() {
        super.onStart()
        when {
            PermissionUtils.isAccessFineLocationGranted(requireContext()) -> {
                when {
                    PermissionUtils.isLocationEnabled(requireContext()) -> {
                        if (isNetworkAvailable())
                            setUpLocationListener()
                    }
                }
            }
            else -> {
                PermissionUtils.requestAccessFineLocationPermission(
                    requireActivity() as AppCompatActivity,
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    when {
                        PermissionUtils.isLocationEnabled(requireContext()) -> {
                            setUpLocationListener()
                        }
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Permission not granted",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun setUpLocationListener() {
        val ct = CancellationTokenSource()
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, ct.token)
            .addOnCompleteListener {
                coordinates = "${it.result.latitude},${it.result.longitude}"
                Log.d("Coordinates", coordinates)

            }
        // for getting the current location update after every 2 seconds with high accuracy
        /* val locationRequest = LocationRequest().setInterval(2000).setFastestInterval(2000)
             .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
         fusedLocationClient.requestLocationUpdates(
             locationRequest,
             object : LocationCallback() {
                 override fun onLocationResult(locationResult: LocationResult) {
                     super.onLocationResult(locationResult)
                     currentLocation = locationResult.lastLocation

                     var lat = currentLocation!!.latitude
                     var long = currentLocation!!.longitude

                     coordinates = "$lat,$long"

                     Log.d("Coordinates", coordinates)

                 }
             },
             Looper.myLooper()!!
         )*/
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    companion object {
        @JvmStatic
        fun newInstance() = CurrentWeatherScreen()
    }
}
