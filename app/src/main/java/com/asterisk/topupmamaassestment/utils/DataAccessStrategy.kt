package com.asterisk.topupmamaassestment.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

fun <ResultType, RequestType> performGetOperation(
    databaseQuery: () -> LiveData<ResultType>,
    networkCall: suspend () -> Resource<RequestType>,
    saveCallResult: suspend (RequestType) -> Unit,
    shouldFetch: () -> Boolean = { true }
): LiveData<Resource<ResultType>> {
    return liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)
            val source = databaseQuery.invoke().map { Resource.success(it) }
            emitSource(source)
        } else if (responseStatus.status == Status.ERROR) {
            emit(Resource.error(responseStatus.message!!))
        }
    }
}

