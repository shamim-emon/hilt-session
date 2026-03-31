package com.emon.hilt_session

sealed class ApiResult<out T> {

    data class Success<T>(
        val data: T
    ) : ApiResult<T>()

    data class Error(
        val errorResponse: String,
    ) : ApiResult<Nothing>()

    object Loading : ApiResult<Nothing>()
}