package com.sgupta.core.flows

import com.sgupta.core.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import retrofit2.Response

fun <T, R> toResponseFlow(
    apiCall: suspend () -> Response<T>,
    mapper: (T?) -> R?
): Flow<Resource<R>> = flow {
    try {
        emit(Resource.Loading)
        val response = apiCall()
        if (response.isSuccessful) {
            emit(Resource.Success(mapper(response.body())))
        } else {
            emit(Resource.Error(Throwable(response.errorBody()?.string() ?: "Unknown Error")))
        }
    } catch (e: Exception) {
        emit(Resource.Error(e))
    }
}

suspend fun <T, R> toPageSource(
    apiCall: suspend () -> Response<T>,
    mapper: (T?) -> R?
) : Resource<R>{
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            Resource.Success(mapper(response.body()))
        } else {
            Resource.Error(Throwable(response.errorBody()?.string() ?: "Unknown Error"))
        }
    } catch (e: Exception) {
        Resource.Error(e)
    }
}

inline fun <T> Flow<Resource<T>>.onLoading(crossinline action: suspend () -> Unit) = this
    .onEach { if (it is Resource.Loading) action.invoke() }

inline fun <T> Flow<Resource<T>>.onSuccess(crossinline action: suspend (T?) -> Unit) = this
    .onEach { if (it is Resource.Success) action(it.data) }

inline fun <T> Flow<Resource<T>>.onError(crossinline action: suspend (Throwable) -> Unit) = this
    .onEach { if (it is Resource.Error) action(it.error) }