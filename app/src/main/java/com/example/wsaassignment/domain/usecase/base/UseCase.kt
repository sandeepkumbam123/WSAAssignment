package com.example.wsaassignment.domain.usecase.base

import com.example.wsaassignment.di.Default
import com.example.wsaassignment.di.IO
import com.example.wsaassignment.util.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.wsaassignment.domain.result.Result

abstract class UseCase<out DerivedMethodParam : UseCase.MethodParam>(
    @Default protected val default: CoroutineDispatcher,
    @IO protected val io: CoroutineDispatcher
) {

    interface MethodParam

    abstract suspend fun <Type> execute(mp: @UnsafeVariance DerivedMethodParam): Type

    operator fun <Type> invoke(
        scope: CoroutineScope,
        mp: @UnsafeVariance DerivedMethodParam,
        onSuccess: ((Type) -> Unit)? = null,
        onError: ((error: Throwable, response: String?) -> Unit)? = null
    ) {
        scope.launch {
            try {
                withContext(io) {
                    execute<Type>(mp)
                }?.let {
                    onSuccess?.invoke(it)
                } ?: run {
                    onError?.invoke(Throwable(Constants.SOMETHING_WENT_WRONG), null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onError?.invoke(e, e.localizedMessage)
            }
        }
    }

    suspend operator fun <Type> invoke(
        mp: @UnsafeVariance DerivedMethodParam,
        dispatcher: CoroutineDispatcher = io
    )
            : Result<Type> = try {
        Result.Success(data = withContext(dispatcher) {
            execute(mp)
        })
    } catch (e: Exception) {
        e.printStackTrace()
        Result.Failure(throwable = e, e.localizedMessage)

    }
}