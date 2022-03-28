package com.asterisk.topupmamaassestment.data.repository

import com.asterisk.topupmamaassestment.data.local.ForecastDao
import com.asterisk.topupmamaassestment.data.models.local.ForecastResponse
import com.asterisk.topupmamaassestment.data.remote.WeatherDataSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val remoteDataSource: WeatherDataSource,
    private val localDataSource: ForecastDao
) : IForecastRepository {

    override suspend fun getForecast(cities: List<String>): List<ForecastResponse> {
        val list = ArrayList<com.asterisk.topupmamaassestment.data.models.remote.ForecastResponse>()
        var uiForecast = listOf<ForecastResponse>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var response: Deferred<com.asterisk.topupmamaassestment.data.models.remote.ForecastResponse>
                for (city in cities) {
                    response = async { remoteDataSource.getForecast(city) }
                    list.addAll(listOf(response.await()))
                }
                val favForecast = localDataSource.getFavForecast().first()
                uiForecast = list.map { serverResponse ->
                    val isFav = favForecast.any {
                        it.name == serverResponse.name
                    }

                    ForecastResponse(
                        base = serverResponse.base,
                        clouds = serverResponse.clouds,
                        cod = serverResponse.cod,
                        coord = serverResponse.coord,
                        dt = serverResponse.dt,
                        id = serverResponse.id,
                        main = serverResponse.main,
                        name = serverResponse.name,
                        sys = serverResponse.sys,
                        timezone = serverResponse.timezone,
                        visibility = serverResponse.visibility,
                        weather = serverResponse.weather,
                        wind = serverResponse.wind,
                        isFavourite = isFav
                    )
                }
                localDataSource.insert(uiForecast)
            }catch (e: Exception) {
                throw e
            }
        }
        delay(10000)
        return uiForecast
    }

    override fun getLocalForecast() = localDataSource.getForecast()

    override fun searchForecast(string: String) = localDataSource.searchForecast(string)

    override suspend fun update(updatedForecast: ForecastResponse) =
        localDataSource.update(updatedForecast)

}
