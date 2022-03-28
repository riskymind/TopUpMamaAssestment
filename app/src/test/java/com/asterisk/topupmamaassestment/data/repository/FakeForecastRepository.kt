package com.asterisk.topupmamaassestment.data.repository

import com.asterisk.topupmamaassestment.data.models.local.ForecastResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeForecastRepository : IForecastRepository {

    private val forecast = mutableListOf<ForecastResponse>()

    private val flowOfForecast = MutableStateFlow<List<ForecastResponse>>(forecast)

    override suspend fun getForecast(cities: List<String>): List<ForecastResponse> {
        return forecast
    }

    override fun getLocalForecast(): List<ForecastResponse> {
        return forecast
    }

    override fun searchForecast(string: String): Flow<List<ForecastResponse>> {
        return flowOfForecast
    }

    override suspend fun update(updatedForecast: ForecastResponse) {
        
    }


}