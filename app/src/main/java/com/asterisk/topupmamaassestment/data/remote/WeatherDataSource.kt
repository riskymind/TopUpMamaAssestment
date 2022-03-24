package com.asterisk.topupmamaassestment.data.remote

import javax.inject.Inject

class WeatherDataSource @Inject constructor(
    private val weatherService: WeatherService
) : BaseDataSource() {

    suspend fun getForecast(string: String) =
        weatherService.fetchWeatherForecast(string)
//        getResult { weatherService.fetchWeatherForecast(string) }
}