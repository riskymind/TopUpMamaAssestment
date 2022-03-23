package com.asterisk.topupmamaassestment.data.remote

import com.asterisk.topupmamaassestment.data.models.ForecastResponse
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
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String = API_KEY
    ): Response<ForecastResponse>


    companion object {

        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        private const val API_KEY = "d74edc7930e2dd36ef98bb9080aa1f11"

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