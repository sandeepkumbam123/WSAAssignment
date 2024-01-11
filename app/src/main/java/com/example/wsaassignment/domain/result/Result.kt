package com.example.wsaassignment.domain.result


sealed  class Result<out T> {
    class Success<out T>(val data : T) : Result<T>()

    class Failure(val throwable : Throwable? = null,
        val response : String? = null) : Result<Nothing>()
}