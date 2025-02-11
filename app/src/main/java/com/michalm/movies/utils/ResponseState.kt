package com.michalm.movies.utils

sealed class ResponseState {
    data object Loading : ResponseState()
    data object Success : ResponseState()
    data class Error(val message: String) : ResponseState()
}