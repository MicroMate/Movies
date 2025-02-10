package com.michalm.movies.utils

sealed class ResponseState<out T> {
    data class Success<T>(val data: T) : ResponseState<T>()
    data class Error(val message: String) : ResponseState<Nothing>()
}
