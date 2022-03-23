package com.asterisk.topupmamaassestment.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.asterisk.topupmamaassestment.data.repository.ForecastRepository
import com.asterisk.topupmamaassestment.utils.Cities
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository
) : ViewModel() {

    private val eventChannel = Channel<ForecastEvent>()
    val forecastEvents = eventChannel.receiveAsFlow()

    private val refreshTriggerChannel = Channel<Refresh>()
    private val refreshTrigger = refreshTriggerChannel.receiveAsFlow()

    init {
        getTwentyCities()
    }

    private val query = MutableStateFlow("")
    var searchQuery = false


    val forecast = query.flatMapLatest { string ->
        forecastRepository.getForecast(
            string = string,
            onFetchFailed = { throwable ->
                viewModelScope.launch {
                    eventChannel.send(ForecastEvent.ShowErrorMessage(throwable))
                }
            },
            forceRefresh = false
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    val searchForecast = query.flatMapLatest { string ->
        forecastRepository.searchForecast(
            string = string,
            onFetchFailed = { throwable ->
                viewModelScope.launch {
                    eventChannel.send(ForecastEvent.ShowErrorMessage(throwable))
                }
            },
            forceRefresh = false
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)




    private fun getTwentyCities() = CoroutineScope(Dispatchers.IO).launch {
        for (city in Cities.listOfCities) {
            val result = async { getQueryString(city) }
            result.await()
        }
    }

    private fun getQueryString(string: String) = viewModelScope.launch {
        query.value = string
    }

    fun getSearch(string: String) = viewModelScope.launch {
        query.value = string
    }


    enum class Refresh {
        FORCE, NORMAL
    }

    sealed class ForecastEvent {
        data class ShowErrorMessage(val error: Throwable) : ForecastEvent()
    }

}