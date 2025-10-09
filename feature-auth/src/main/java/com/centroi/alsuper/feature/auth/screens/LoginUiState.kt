package com.centroi.alsuper.feature.auth.screens

sealed class LoginUiState<T>(val data: T? = null, val message: String? = null) {
    class Idle<T> : LoginUiState<T>()
    class Loading<T> : LoginUiState<T>()
    class Success<T>(data: T) : LoginUiState<T>(data)
    class Error<T>(message: String, data: T? = null) : LoginUiState<T>(data, message)
}