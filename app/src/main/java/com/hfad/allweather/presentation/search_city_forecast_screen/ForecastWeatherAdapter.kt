package com.hfad.allweather.presentation.search_city_forecast_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.hfad.allweather.R
import com.hfad.allweather.databinding.ForecastListItemBinding
import com.hfad.allweather.domain.model.forecast_weather.Forecastday
import com.hfad.allweather.domain.model.forecast_weather.HourForecastWeather
import com.squareup.picasso.Picasso

class ForecastWeatherAdapter :
    RecyclerView.Adapter<ForecastWeatherAdapter.SearchHolder>() {

    var items: List<HourForecastWeather> = arrayListOf()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()

        }

    class SearchHolder(item: View) : RecyclerView.ViewHolder(item) {

       private val binding = ForecastListItemBinding.bind(item)

        fun bind(forecastHour: HourForecastWeather) = with(binding){

            tvDate.text = forecastHour.time
            Picasso.get().load( "https:" + forecastHour.condition.icon).into(imCondition)
            tvTemp.text = forecastHour.temp_c.toString()
            tvCondition.text = forecastHour.condition.text

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.forecast_list_item, parent, false)

        return SearchHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

}