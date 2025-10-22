package com.centroi.alsuper.feature.contacts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.centroi.alsuper.core.data.ResultState
import com.centroi.alsuper.core.data.models.panic.PanicResponse
import com.centroi.alsuper.core.data.usecases.panic.SendPanicSosUseCase
import com.centroi.alsuper.core.ui.components.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class PanicScreenViewModelAbstract : ViewModel() {
    abstract fun sendSos()
    abstract fun resetState()
    abstract val uiState: StateFlow<UiState<PanicResponse>>
}

@HiltViewModel
class PanicScreenViewModel @Inject constructor(
    private val sendSosUseCase: SendPanicSosUseCase
) : PanicScreenViewModelAbstract() {

    private val _uiState = MutableStateFlow<UiState<PanicResponse>>(UiState.Idle())
    override val uiState: StateFlow<UiState<PanicResponse>> = _uiState.asStateFlow()

    override fun sendSos() {
        viewModelScope.launch {
            sendSosUseCase().collect { resultState ->
                when (resultState) {
                    is ResultState.Error -> _uiState.value =
                        UiState.Error(resultState.message.orEmpty())
                    is ResultState.Loading -> _uiState.value = UiState.Loading()
                    is ResultState.Success ->{
                        resultState.data?.let {
                            _uiState.value = UiState.Success(it)
                        }
                    }
                }
            }
        }
    }

    override fun resetState() {
        _uiState.value = UiState.Idle()
    }
}