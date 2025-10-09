package com.centroi.alsuper.core.ui.components

sealed class UiState<T>(val data: T? = null, val message: String? = null) {
    class Idle<T> : UiState<T>()
    class Loading<T> : UiState<T>()
    class Success<T>(data: T) : UiState<T>(data)
    class Error<T>(message: String, data: T? = null) : UiState<T>(data, message)
}