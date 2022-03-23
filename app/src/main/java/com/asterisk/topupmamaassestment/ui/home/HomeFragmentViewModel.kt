package com.asterisk.topupmamaassestment.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.asterisk.topupmamaassestment.data.repository.ForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository
) : ViewModel() {

//    var userInput: String = ""

    private val query = MutableLiveData<String>("")

    val forecast = query.switchMap { string ->
        forecastRepository.getForecast(string)
    }

//    val forecast = _forecast

    fun getQueryString(string: String) {
        query.value = string
    }

}