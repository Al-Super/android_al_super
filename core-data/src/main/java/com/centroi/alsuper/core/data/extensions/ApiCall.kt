package com.centroi.alsuper.core.data.extensions

import com.centroi.alsuper.core.data.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

object ApiCall {

    fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Flow<ResultState<T>> =
        flow {
        emit(ResultState.Loading())
        try {
            val response = apiCall()

            if (response.isSuccessful) {
                response.body()?.let { data ->
                    emit(ResultState.Success(data))
                } ?: emit(ResultState.Error("Response body is null"))
            } else {
                emit(ResultState.Error("${response.errorBody()}"))
            }
        } catch (e: HttpException) {
            emit(ResultState.Error("An unexpected error occurred. Please try again."))
        } catch (e: IOException) {
            emit(ResultState.Error("Couldn't reach the server. Check your internet connection."))
        }
    }

}