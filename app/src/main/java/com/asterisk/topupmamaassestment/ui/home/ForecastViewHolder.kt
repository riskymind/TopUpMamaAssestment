package com.asterisk.topupmamaassestment.ui.home

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.asterisk.topupmamaassestment.R
import com.asterisk.topupmamaassestment.data.models.local.ForecastResponse
import com.asterisk.topupmamaassestment.databinding.ForecastListItemBinding
import com.bumptech.glide.Glide

class ForecastViewHolder(
    private val binding: ForecastListItemBinding,
    private val onFavIconClicked: (Int) -> Unit,
    private val itemClicked: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {

        binding.apply {
            ivFavHeart.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onFavIconClicked(position)
                }
            }

            root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClicked(position)
                }
            }
        }
    }

    fun bind(forecastResponse: ForecastResponse) {
        val context: Context = binding.root.context

        val icon: String = forecastResponse.weather[0].icon
        val iconUrl = "http://openweathermap.org/img/w/$icon.png"

        binding.apply {
            tvName.text = context.getString(R.string.city, forecastResponse.name)
            tvCountry.text = context.getString(R.string.country, forecastResponse.sys.country)
            temp.text =
                context.getString(R.string.temp, forecastResponse.main.temp.toString())
            Glide.with(context)
                .load(iconUrl)
                .error(R.drawable.ic_error)
                .into(ivWeatherIcon)
            ivFavHeart.setImageResource(
                when {
                    forecastResponse.isFavourite -> R.drawable.ic_love_heart
                    else -> R.drawable.ic_ios_heart
                }
            )
        }
    }
}