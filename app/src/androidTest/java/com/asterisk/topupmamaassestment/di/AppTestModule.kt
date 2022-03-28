package com.asterisk.topupmamaassestment.di

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.asterisk.topupmamaassestment.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppTestModule {

    @Provides
    @Named("testDB")
    fun provideInMemoryDB(
        @ApplicationContext context: Context
    ) =
        Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
}