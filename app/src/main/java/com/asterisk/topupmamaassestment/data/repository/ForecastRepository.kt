package com.asterisk.topupmamaassestment.data.repository

import com.asterisk.topupmamaassestment.data.local.ForecastDao
import com.asterisk.topupmamaassestment.data.remote.WeatherDataSource
import com.asterisk.topupmamaassestment.utils.AppUtils
import com.asterisk.topupmamaassestment.utils.Cities
import com.asterisk.topupmamaassestment.utils.Resource
import com.asterisk.topupmamaassestment.utils.performGetOperation
import kotlinx.coroutines.*
import okhttp3.Dispatcher
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val remoteDataSource: WeatherDataSource,
    private val localDataSource: ForecastDao
) {
    private lateinit var result: Job

    fun getForecast(string: String) = performGetOperation(
        databaseQuery = { localDataSource.getForecast() },
        networkCall = { remoteDataSource.getForecast(string) },
        saveCallResult = {
            localDataSource.insert(it)
        }
    )

    fun getLocalData() = localDataSource.getForecast()
}
