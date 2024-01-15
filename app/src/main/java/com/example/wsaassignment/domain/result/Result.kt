package com.example.wsaassignment.domain.result

import com.example.wsaassignment.data.model.BaseResponse


sealed  class Result<out T> {
    class Success<out T>(val data : T) : Result<T>()

    class Failure(val throwable : Throwable? = null,
        val response : BaseResponse? = null) : Result<Nothing>()
}