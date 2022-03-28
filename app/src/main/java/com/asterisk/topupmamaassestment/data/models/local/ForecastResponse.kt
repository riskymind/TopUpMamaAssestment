package com.asterisk.topupmamaassestment.data.models.local


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.asterisk.topupmamaassestment.data.models.remote.*
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.DateFormat

@Entity(tableName = "weather_table")
data class ForecastResponse(
    @ColumnInfo(name = "base")
    val base: String,
    @ColumnInfo(name = "clouds")
    val clouds: Clouds,
    @ColumnInfo(name = "cod")
    val cod: Int,
    @ColumnInfo(name = "coord")
    val coord: Coord,
    @ColumnInfo(name = "dt")
    val dt: Int,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "main")
    val main: Main,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "sys")
    val sys: Sys,
    @ColumnInfo(name = "timezone")
    val timezone: Int,
    @ColumnInfo(name = "visibility")
    val visibility: Int,
    @ColumnInfo(name = "weather")
    val weather: List<Weather>,
    @ColumnInfo(name = "wind")
    val wind: Wind,
    @ColumnInfo(name = "isFavourite")
    val isFavourite: Boolean = false
) : Serializable {
    val getFormattedDT: String
        get() = DateFormat.getDateInstance().format(dt)
}