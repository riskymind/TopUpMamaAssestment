package com.asterisk.topupmamaassestment.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

//fun <ResultType, RequestType> performGetOperation(
//    databaseQuery: () -> LiveData<ResultType>,
//    networkCal: suspend () -> Resource<RequestType>,
//    saveCallResult: suspend (RequestType) -> Unit,
//    shouldFetch: (ResultType) -> Boolean = { true },
//    onFetchFailed: (Throwable) -> Unit = {}
//): LiveData<Resource<ResultType>> {
//    return liveData(Dispatchers.IO) {
//        emit(Resource.loading())
//        val responseStatus = networkCal.invoke()
//        if (responseStatus.status == Status.SUCCESS) {
//            saveCallResult(responseStatus.data!!)
//            val source = databaseQuery.invoke().map { Resource.success(it) }
//            emitSource(source)
//        } else if (responseStatus.status == Status.ERROR) {
//            emit(Resource.error(responseStatus.message!!))
//        }
//    }
//}

fun <ResultType, RequestType> performGetOperation(
    databaseQuery: () -> Flow<ResultType>,
    networkCall: suspend () -> RequestType,
    saveCallResult: suspend (RequestType) -> Unit,
    shouldFetch: (ResultType) -> Boolean = { true },
    onFetchFailed: (Throwable) -> Unit = {}
) = channelFlow {

    val data = databaseQuery().first()

    if (shouldFetch(data)) {
        val loading = launch {
            databaseQuery().collect { send(Resource.loading(it)) }
        }

        try {
            saveCallResult(networkCall())
            loading.cancel()
            databaseQuery().collect { send(Resource.success(it)) }
        } catch (throwable: Throwable) {
            onFetchFailed(throwable)
            loading.cancel()
            databaseQuery().collect { send(Resource.error(throwable.message, it)) }
        }
    } else {
        databaseQuery().collect { send(Resource.success(it)) }
    }
}

//fun <ResultType> performGetOperations(
//    databaseQuery: () -> Flow<ResultType>
//) = channelFlow {
//
//    databaseQuery().first()
//
//    databaseQuery().collect { send(Resource.success(it)) }
//}