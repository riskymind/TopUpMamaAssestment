package com.asterisk.topupmamaassestment.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.asterisk.topupmamaassestment.data.models.ForecastResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao {

    @Query("SELECT * FROM weather_table")
    fun getForecast(): Flow<List<ForecastResponse>>

    @Query("SELECT * FROM weather_table WHERE name LIKE '%' || :string || '%'")
    fun searchForecast(string: String): Flow<List<ForecastResponse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(forecastResponse: ForecastResponse)

    @Query("DELETE FROM weather_table")
    suspend fun delete()
}