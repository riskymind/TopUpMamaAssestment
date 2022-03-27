package com.asterisk.topupmamaassestment.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.asterisk.topupmamaassestment.data.models.local.ForecastResponse
import com.asterisk.topupmamaassestment.data.models.remote.*
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ForecastDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var forecastDao: ForecastDao

    @Before
    fun createDB() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        forecastDao = database.foreCastDao()
    }

    @After
    fun closeDB() {
        database.close()
    }


    @Test
    fun getListOfForecast_returnTrueIfEmpty() = runTest {
        val allForecast = forecastDao.getForecast()
        assertThat(allForecast).isEmpty()
    }

    @Test
    fun getListOfForecast_returnTrueIfNotEmpty() = runTest {
        val forecastResponse = ForecastResponse(
            base = "anything",
            clouds = Clouds(1),
            cod = 1,
            coord = Coord(1.3, 2.1),
            dt = 2,
            main = Main(1.2, 2, 1, 3.1, 1.2, 3.1),
            name = "fake",
            sys = Sys("Ng", 1, 2, 1, 3),
            timezone = 3,
            visibility = 1,
            weather = listOf(Weather("no desc", "icon", 2, "mainString")),
            wind = Wind(1, 2.1),
            isFavourite = false,
            id = 2
        )
        forecastDao.insert(listOf(forecastResponse))
        val allForecast = forecastDao.getForecast()
        assertThat(allForecast).isNotEmpty()
    }


    @Test
    fun insertListOfForecast() = runTest {
        val forecastResponse = ForecastResponse(
            base = "anything",
            clouds = Clouds(1),
            cod = 1,
            coord = Coord(1.3, 2.1),
            dt = 2,
            main = Main(1.2, 2, 1, 3.1, 1.2, 3.1),
            name = "fake",
            sys = Sys("Ng", 1, 2, 1, 3),
            timezone = 3,
            visibility = 1,
            weather = listOf(Weather("no desc", "icon", 2, "mainString")),
            wind = Wind(1, 2.1),
            isFavourite = false,
            id = 2
        )

        forecastDao.insert(listOf(forecastResponse))

        val allForecast = forecastDao.getForecast()

        assertThat(allForecast).contains(forecastResponse)
    }

    @Test
    fun deleteListOfForecast() = runTest {
        val forecastResponse = ForecastResponse(
            base = "anything",
            clouds = Clouds(1),
            cod = 1,
            coord = Coord(1.3, 2.1),
            dt = 2,
            main = Main(1.2, 2, 1, 3.1, 1.2, 3.1),
            name = "fake",
            sys = Sys("Ng", 1, 2, 1, 3),
            timezone = 3,
            visibility = 1,
            weather = listOf(Weather("no desc", "icon", 2, "mainString")),
            wind = Wind(1, 2.1),
            isFavourite = false,
            id = 2
        )
        forecastDao.insert(listOf(forecastResponse))
        forecastDao.delete()

        val allForecast = forecastDao.getForecast()
        assertThat(allForecast).isEmpty()
    }

    @Test
    fun updateForecast() = runTest {
        val forecastResponse = ForecastResponse(
            base = "anything",
            clouds = Clouds(1),
            cod = 1,
            coord = Coord(1.3, 2.1),
            dt = 2,
            main = Main(1.2, 2, 1, 3.1, 1.2, 3.1),
            name = "fake",
            sys = Sys("Ng", 1, 2, 1, 3),
            timezone = 3,
            visibility = 1,
            weather = listOf(Weather("no desc", "icon", 2, "mainString")),
            wind = Wind(1, 2.1),
            isFavourite = false,
            id = 2
        )
        forecastDao.update(forecastResponse)

        val allForecast = forecastDao.getForecast()
        assertThat(allForecast).isNotSameInstanceAs(forecastResponse)
    }

}