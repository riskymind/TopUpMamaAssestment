package com.asterisk.topupmamaassestment.data.local

import androidx.room.TypeConverter
import com.asterisk.topupmamaassestment.data.models.local.*
import com.asterisk.topupmamaassestment.data.models.remote.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromClouds(clouds: Clouds): String = Gson().toJson(clouds)

    @TypeConverter
    fun toClouds(string: String): Clouds = Gson().fromJson(string, Clouds::class.java)

    @TypeConverter
    fun fromCoord(coord: Coord): String = Gson().toJson(coord)

    @TypeConverter
    fun toCoord(string: String): Coord = Gson().fromJson(string, Coord::class.java)


    @TypeConverter
    fun fromMain(main: Main): String = Gson().toJson(main)

    @TypeConverter
    fun toMain(string: String): Main = Gson().fromJson(string, Main::class.java)

    @TypeConverter
    fun fromSys(sys: Sys): String = Gson().toJson(sys)

    @TypeConverter
    fun toSys(string: String): Sys = Gson().fromJson(string, Sys::class.java)

    @TypeConverter
    fun fromWind(wind: Wind): String = Gson().toJson(wind)

    @TypeConverter
    fun toWind(string: String): Wind = Gson().fromJson(string, Wind::class.java)


    @TypeConverter
    fun fromWeather(weather: List<Weather?>?): String? {
        if (weather == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Weather?>?>() {}.type
        return gson.toJson(weather, type)
    }

    @TypeConverter
    fun toWeather(string: String?): List<Weather>? {
        if (string == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Weather?>?>() {}.type
        return gson.fromJson<List<Weather>>(string, type)
    }

}