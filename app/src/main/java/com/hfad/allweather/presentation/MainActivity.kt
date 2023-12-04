package com.hfad.allweather.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.hfad.allweather.R
import com.hfad.allweather.databinding.ActivityMainBinding
import com.hfad.allweather.presentation.current_weather_screen.CurrentWeatherScreen
import com.hfad.allweather.presentation.search_city_forecast_screen.ForecastWeatherScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val listFragments = listOf(
        CurrentWeatherScreen(),
        ForecastWeatherScreen()
    )
    private val tabList = listOf(
        "Current Location", "Search City"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        viewPagerInit()

    }


    private fun viewPagerInit() = with(binding) {
        val vP = FragmentAdapter(this@MainActivity as FragmentActivity, listFragments)
        viewPager.adapter = vP

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabList[position]
        }.attach()
    }

}