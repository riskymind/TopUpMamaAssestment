package com.asterisk.topupmamaassestment.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.asterisk.topupmamaassestment.R
import com.asterisk.topupmamaassestment.data.models.local.ForecastResponse
import com.asterisk.topupmamaassestment.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val forecast by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)

        setUpView(forecast.forecast)

    }

    private fun setUpView(forecast: ForecastResponse) {
        binding.apply {
            tvCityName.text = forecast.name
            tvCountryName.text = forecast.sys.country
            tvDate.text = forecast.dt.toString()
            tvTemp.text = forecast.main.temp.toString()
            tvDescription.text = forecast.weather[0].description
            tvFeelsLike.text = forecast.main.feelsLike.toString()
            tvPressure.text = forecast.main.pressure.toString()
            tvHumidity.text = forecast.main.humidity.toString()
            tvWind.text = forecast.wind.speed.toString()

            val icon: String = forecast.weather[0].icon
            val iconUrl = "http://openweathermap.org/img/w/$icon.png"

            Glide.with(requireContext())
                .load(iconUrl)
                .centerCrop()
                .error(R.drawable.ic_error)
                .into(ivWeatherIcon)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}