package com.asterisk.topupmamaassestment.ui.home

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.asterisk.topupmamaassestment.R
import com.asterisk.topupmamaassestment.data.models.ForecastResponse
import com.asterisk.topupmamaassestment.databinding.ForecastListItemBinding
import com.bumptech.glide.Glide

class ForecastViewHolder(private val binding: ForecastListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(forecastResponse: ForecastResponse) {
        val context: Context = binding.root.context

        val icon: String = forecastResponse.weather[0].icon
        val iconUrl = "http://openweathermap.org/img/w/$icon.png"

        binding.apply {
            tvName.text = context.getString(R.string.name, forecastResponse.name)
            tvCountry.text = context.getString(R.string.country, forecastResponse.sys.country)
            temp.text =
                context.getString(R.string.temp, forecastResponse.main.temp.toString())
            Glide.with(context)
                .load(iconUrl)
                .error(R.drawable.ic_error)
                .into(ivWeatherIcon)
        }
    }
}