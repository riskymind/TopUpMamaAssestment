package com.asterisk.topupmamaassestment.di

import android.content.Context
import com.asterisk.topupmamaassestment.data.remote.WeatherService
import com.asterisk.topupmamaassestment.utils.AppUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideWeatherService(): WeatherService =
        WeatherService.create()

    @Provides
    fun provideAppUtils(
        @ApplicationContext context: Context
    ): AppUtils =
        AppUtils(context)

}