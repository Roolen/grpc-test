package com.example.grpctest.utils

sealed class DataResult<out T>
object Loading : DataResult<Nothing>()
data class Success<out T>(val data: T) : DataResult<T>()
data class ErrorResult(val message: String) : DataResult<Nothing>()
data class Failed(val message: String) : DataResult<Nothing>()
