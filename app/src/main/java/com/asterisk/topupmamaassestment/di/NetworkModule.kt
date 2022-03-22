package com.asterisk.topupmamaassestment.di

import com.asterisk.topupmamaassestment.data.remote.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideWeatherService(): WeatherService =
        WeatherService.create()
}