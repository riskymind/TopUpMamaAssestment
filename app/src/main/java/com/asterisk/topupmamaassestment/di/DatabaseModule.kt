package com.asterisk.topupmamaassestment.di

import android.content.Context
import com.asterisk.topupmamaassestment.data.local.AppDatabase
import com.asterisk.topupmamaassestment.data.local.ForecastDao
import com.asterisk.topupmamaassestment.data.remote.WeatherDataSource
import com.asterisk.topupmamaassestment.data.remote.WeatherService
import com.asterisk.topupmamaassestment.data.repository.ForecastRepository
import com.asterisk.topupmamaassestment.data.repository.IForecastRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase =
        AppDatabase.getDatabase(context)

    @Provides
    fun provideForecastDao(db: AppDatabase) =
        db.foreCastDao()

    @Provides
    @Singleton
    fun provideForecastRepository(
        api: WeatherDataSource,
        dao: ForecastDao
    ) = ForecastRepository(api, dao) as IForecastRepository
}