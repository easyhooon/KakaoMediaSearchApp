package com.kenshi.data.util

import com.kenshi.domain.util.ApiResult
import io.ktor.client.plugins.ResponseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> safeFlow(execute: suspend () -> T): Flow<ApiResult<T>> = flow {
    try {
        val result = execute.invoke()
        emit(ApiResult.Success(result))
    } catch (exception: ResponseException) {
        emit(ApiResult.Error(code = exception.response.status.value, message = exception.message))
    } catch (exception: Exception) {
        emit(ApiResult.Exception(exception = exception))
    }
}