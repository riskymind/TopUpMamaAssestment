package com.asterisk.topupmamaassestment.data.repository

import com.asterisk.topupmamaassestment.data.local.ForecastDao
import com.asterisk.topupmamaassestment.data.remote.WeatherDataSource
import com.asterisk.topupmamaassestment.utils.performGetOperation
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val remoteDataSource: WeatherDataSource,
    private val localDataSource: ForecastDao
) {
    fun getForecast(
        string: String,
        onFetchFailed: (Throwable) -> Unit,
        forceRefresh: Boolean,
    ) = performGetOperation(
        databaseQuery = { localDataSource.getForecast() },
        networkCall = {
            val response = remoteDataSource.getForecast(string)
            response.data
        },
        saveCallResult = { serverResult ->
            localDataSource.delete()
            if (serverResult != null) {
                localDataSource.insert(serverResult)
            }
        },
        shouldFetch = {
            forceRefresh
        },

        onFetchFailed = { throwable ->
            if (throwable !is HttpException && throwable !is IOException) {
                throw throwable
            }
            onFetchFailed(throwable)
        }
    )

    fun searchForecast(
        string: String,
        onFetchFailed: (Throwable) -> Unit,
        forceRefresh: Boolean = false,
    ) = performGetOperation(
        databaseQuery = { localDataSource.searchForecast(string) },
        networkCall = {
            val response = remoteDataSource.getForecast(string)
            response.data
        },
        saveCallResult = { serverResult ->
            localDataSource.delete()
            if (serverResult != null) {
                localDataSource.insert(serverResult)
            }
        },
        shouldFetch = {
            forceRefresh
        },

        onFetchFailed = { throwable ->
            if (throwable !is HttpException && throwable !is IOException) {
                throw throwable
            }
            onFetchFailed(throwable)
        }
    )
}