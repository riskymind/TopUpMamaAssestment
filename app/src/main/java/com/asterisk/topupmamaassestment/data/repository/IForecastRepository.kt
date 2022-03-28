package com.asterisk.topupmamaassestment.data.repository

import com.asterisk.topupmamaassestment.data.models.local.ForecastResponse
import kotlinx.coroutines.flow.Flow

interface IForecastRepository {

    suspend fun getForecast(cities: List<String>): List<ForecastResponse>
    fun getLocalForecast(): List<ForecastResponse>
    fun searchForecast(string: String): Flow<List<ForecastResponse>>
    suspend fun update(updatedForecast: ForecastResponse)
}