package com.asterisk.topupmamaassestment.data.repository

import com.asterisk.topupmamaassestment.data.local.AppDatabase
import com.asterisk.topupmamaassestment.data.local.ForecastDao
import com.asterisk.topupmamaassestment.data.remote.WeatherDataSource
import com.asterisk.topupmamaassestment.utils.performGetOperation
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val remoteDataSource: WeatherDataSource,
    private val localDataSource: ForecastDao
) {
    fun getForecast(string: String) = performGetOperation(
        databaseQuery = { localDataSource.getForecast() },
        networkCal = { remoteDataSource.getForecast(string) },
        saveCallResult = { localDataSource.insert(it) }
    )

}