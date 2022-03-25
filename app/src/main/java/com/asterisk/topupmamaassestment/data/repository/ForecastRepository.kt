package com.asterisk.topupmamaassestment.data.repository

import com.asterisk.topupmamaassestment.data.local.ForecastDao
import com.asterisk.topupmamaassestment.data.models.local.ForecastResponse
import com.asterisk.topupmamaassestment.data.remote.WeatherDataSource
import kotlinx.coroutines.*
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val remoteDataSource: WeatherDataSource,
    private val localDataSource: ForecastDao
) {

    suspend fun getForecast(cities: List<String>): List<ForecastResponse> {
        val list = ArrayList<com.asterisk.topupmamaassestment.data.models.remote.ForecastResponse>()
        var uiForecast = listOf<ForecastResponse>()
        CoroutineScope(Dispatchers.IO).launch {
            var response: Deferred<com.asterisk.topupmamaassestment.data.models.remote.ForecastResponse>
            for (city in cities) {
                response = async { remoteDataSource.getForecast(city) }
                list.addAll(listOf(response.await()))
            }

            uiForecast = list.map {
                ForecastResponse(
                    base = it.base,
                    clouds = it.clouds,
                    cod = it.cod,
                    coord = it.coord,
                    dt = it.dt,
                    id = it.id,
                    main = it.main,
                    name = it.name,
                    sys = it.sys,
                    timezone = it.timezone,
                    visibility = it.visibility,
                    weather = it.weather,
                    wind = it.wind
                )
            }
            localDataSource.insert(uiForecast)
        }
        delay(10000)
        return uiForecast
    }

    fun getLocalForecast() = localDataSource.getForecast()

    fun searchForecast(string: String) = localDataSource.searchForecast(string)

}
