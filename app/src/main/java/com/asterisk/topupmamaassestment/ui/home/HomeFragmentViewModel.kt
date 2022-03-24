package com.asterisk.topupmamaassestment.ui.home

import androidx.lifecycle.*
import com.asterisk.topupmamaassestment.data.models.ForecastResponse
import com.asterisk.topupmamaassestment.data.repository.ForecastRepository
import com.asterisk.topupmamaassestment.utils.AppUtils
import com.asterisk.topupmamaassestment.utils.Cities
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository,
    private val appUtils: AppUtils
) : ViewModel() {

    private val forecastFlow = MutableStateFlow<List<ForecastResponse>>(emptyList())
    val forecast: Flow<List<ForecastResponse>> = forecastFlow

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (appUtils.isConnected()) {
                val cities = Cities.listOfCities
                val forecastList = forecastRepository.getForecast(cities)
                forecastFlow.value = forecastList
            }else {
//                val cities = Cities.listOfCities
                val forecastList = forecastRepository.getLocalForecast()
                forecastFlow.value = forecastList
            }
        }
    }

}