package com.asterisk.topupmamaassestment.utils

import com.asterisk.topupmamaassestment.data.local.AppDatabase
import com.asterisk.topupmamaassestment.utils.Constants.BASE_URL
import com.asterisk.topupmamaassestment.utils.Constants.UNITS
import com.google.common.truth.Truth.assertThat
import junit.framework.Assert.assertEquals
import org.junit.Test

class BasicUtilTest {

    @Test
    fun testBaseUrl() {
        assertThat("https://api.openweathermap.org/data/2.5/").isEqualTo(BASE_URL)
    }

    @Test
    fun testDBName() {
        assertThat("forecast-db").isEqualTo(AppDatabase.DATABASE_NAME)
    }


    @Test
    fun testUnits() {
        assertThat("metric").isEqualTo(UNITS)
    }

    @Test
    fun testListOfCities() {
        val list = Cities.listOfCities.size
        assertThat(list).isEqualTo(20)
    }
}