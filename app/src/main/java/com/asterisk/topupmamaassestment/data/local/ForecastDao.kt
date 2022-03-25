package com.asterisk.topupmamaassestment.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.asterisk.topupmamaassestment.data.models.local.ForecastResponse
import kotlinx.coroutines.flow.Flow


@Dao
interface ForecastDao {

    @Query("SELECT * FROM weather_table")
    fun getForecast(): List<ForecastResponse>

    @Query("SELECT * FROM weather_table WHERE name LIKE '%' || :string || '%' ORDER BY isFavourite DESC, name")
    fun searchForecast(string: String): Flow<List<ForecastResponse>>

    @Query("SELECT * FROM weather_table WHERE isFavourite = 1")
    fun getFavForecast(): Flow<List<ForecastResponse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(forecastResponse: List<ForecastResponse>)

    @Query("DELETE FROM weather_table")
    suspend fun delete()

    @Update
    suspend fun update(forecastResponse: ForecastResponse)
}