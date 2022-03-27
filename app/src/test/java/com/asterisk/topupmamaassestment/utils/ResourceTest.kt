package com.asterisk.topupmamaassestment.utils


import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ResourceTest {

    @Test
    fun `return Success status`() {
        val result = Resource.success("data")
        assertThat(result).isEqualTo(
            Resource(
                status = Status.SUCCESS,
                data = "data",
                message = null
            )
        )
    }

    @Test
    fun `return if loading data is null`() {
        val result = Resource.loading(null)
        assertThat(result).isEqualTo(Resource(status = Status.LOADING, data = null, message = null))
    }

    @Test
    fun `return if loading data is not null`() {
        val result = Resource.loading("data")
        assertThat(result).isEqualTo(
            Resource(
                status = Status.LOADING,
                data = "data",
                message = null
            )
        )
    }


    @Test
    fun `return if message and data is null`() {
        val result = Resource.error(null, null)
        assertThat(result).isEqualTo(
            Resource(
                status = Status.ERROR,
                data = null,
                message = null
            )
        )
    }

    @Test
    fun `return if message is null and data is not null`() {
        val result = Resource.error(null, "data")
        assertThat(result).isEqualTo(
            Resource(
                status = Status.ERROR,
                data = "data",
                message = null
            )
        )
    }

    @Test
    fun `return if message and data is not null`() {
        val result = Resource.error("msg", "data")
        assertThat(result).isEqualTo(
            Resource(
                status = Status.ERROR,
                data = "data",
                message = "msg"
            )
        )
    }
}