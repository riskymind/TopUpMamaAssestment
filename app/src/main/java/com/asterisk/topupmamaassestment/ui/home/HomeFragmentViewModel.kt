package com.asterisk.topupmamaassestment.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asterisk.topupmamaassestment.data.models.local.ForecastResponse
import com.asterisk.topupmamaassestment.data.repository.ForecastRepository
import com.asterisk.topupmamaassestment.utils.AppUtils
import com.asterisk.topupmamaassestment.utils.Cities
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository,
    private val appUtils: AppUtils
) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    private val forecastFlow =
        MutableStateFlow<List<ForecastResponse>>(
            emptyList()
        )
    val forecast: Flow<List<ForecastResponse>> =
        forecastFlow

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (appUtils.isConnected()) {
                val cities = Cities.listOfCities
                val forecastList = forecastRepository.getForecast(cities)
                forecastFlow.value = forecastList
            } else {
//                val cities = Cities.listOfCities
                val forecastList = forecastRepository.getLocalForecast()
                forecastFlow.value = forecastList
            }
        }
    }

    private val searchForecastFlow = searchQuery.flatMapLatest {
        forecastRepository.searchForecast(it)
    }

    val searchFlow = searchForecastFlow

}