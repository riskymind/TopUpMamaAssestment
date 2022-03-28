package com.asterisk.topupmamaassestment.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asterisk.topupmamaassestment.data.models.local.ForecastResponse
import com.asterisk.topupmamaassestment.data.repository.ForecastRepository
import com.asterisk.topupmamaassestment.utils.AppUtils
import com.asterisk.topupmamaassestment.utils.Cities
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository,
    private val appUtils: AppUtils
) : ViewModel() {

    private val forecastEventChannel = Channel<ForecastEvent>()
    val homeEvent = forecastEventChannel.receiveAsFlow()

    val searchQuery = MutableStateFlow("")

    init {
        getCitiesTemperature()
    }

    private fun getCitiesTemperature() {
        viewModelScope.launch(Dispatchers.IO) {
            if (appUtils.isConnected()) {
                val cities = Cities.listOfCities
                forecastRepository.getForecast(cities)
            } else {
                forecastEventChannel.send(ForecastEvent.ShowHomeError("You are offline"))
            }
        }
    }

    //data flow
    private val searchForecastFlow = searchQuery.flatMapLatest {
        forecastRepository.searchForecast(it)
    }

    val searchFlow = searchForecastFlow

    // favourite click event
    fun onFavClick(forecast: ForecastResponse) {
        val favStatus = forecast.isFavourite
        val updateForecast = forecast.copy(isFavourite = !favStatus)
        viewModelScope.launch {
            forecastRepository.update(updateForecast)
        }
    }

    // navigation event
    fun onItemClicked(forecastResponse: ForecastResponse) = viewModelScope.launch {
        forecastEventChannel.send(ForecastEvent.NavigateToDetailScreen(forecastResponse))
    }


    sealed class ForecastEvent {
        data class NavigateToDetailScreen(val forecastResponse: ForecastResponse) : ForecastEvent()
        data class ShowHomeError(val msg: String) : ForecastEvent()
    }


}