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
    private val forecastRepository: ForecastRepository
) : ViewModel() {

    init {
        getTwentyCities()
    }

    var searching: Boolean = false

    private val _query = MutableLiveData<String>()
    private val _searchQuery = MutableLiveData<String>()

    private val _forecast = _query.switchMap { string ->
        forecastRepository.getForecast(string)
    }

    val forecast = _forecast

    private fun getTwentyCities() = CoroutineScope(Dispatchers.IO).launch {
        for (city in Cities.listOfCities) {
            val result = async { getQueryString(city) }
            result.await()
        }
    }

    private fun getQueryString(string: String) = viewModelScope.launch {
        _query.value = string
    }

    fun getSearchQuery(string: String) = viewModelScope.launch {
        _searchQuery.value = string
    }


}