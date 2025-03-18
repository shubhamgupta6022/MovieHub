package com.sgupta.core.network

sealed class Resource<out R> {
    data class Success<out T>(val data: T?) : Resource<T>()
    data class Error(val error: Throwable, val errorCode: Int = -1) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=${error.message}]"
            Loading -> "Loading"
        }
    }
}

val Resource<*>.succeeded
    get() = this is Resource.Success && data != null

val Resource<*>.failed
    get() = this is Resource.Error

fun <T> Resource<T>.successOr(fallback: T): T {
    return (this as? Resource.Success<T>)?.data ?: fallback
}

val <T> Resource<T>.data: T?
    get() = (this as? Resource.Success)?.data