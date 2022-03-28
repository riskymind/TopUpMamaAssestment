package com.asterisk.topupmamaassestment.data.remote

import com.asterisk.topupmamaassestment.data.models.remote.ForecastResponse
import com.asterisk.topupmamaassestment.utils.Constants.API_KEY
import com.asterisk.topupmamaassestment.utils.Constants.BASE_URL
import com.asterisk.topupmamaassestment.utils.Constants.UNITS
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    suspend fun fetchWeatherForecast(
        @Query("q") query: String,
        @Query("units") units: String = UNITS,
        @Query("appid") apiKey: String = API_KEY
    ): ForecastResponse


    companion object {

        fun create(): WeatherService {
            return composeRetrofitBuilder()
        }


        private fun composeRetrofitBuilder(): WeatherService {
            val logger =
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherService::class.java)
        }
    }


}