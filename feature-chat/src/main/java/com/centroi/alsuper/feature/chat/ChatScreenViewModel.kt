package com.centroi.alsuper.feature.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.centroi.alsuper.core.data.ResultState
import com.centroi.alsuper.core.data.models.chat.ChatQuestionResponse
import com.centroi.alsuper.core.data.usecases.chat.AskChatQuestionUseCase
import com.centroi.alsuper.core.ui.components.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


abstract class ChatScreenViewModelAbstract : ViewModel() {
    abstract fun tryQuestion(message: String)
    abstract fun resetState()
    abstract val uiState: StateFlow<UiState<ChatQuestionResponse>>
}

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    private val askChatQuestionUseCase: AskChatQuestionUseCase
) : ChatScreenViewModelAbstract() {

    private val _uiState = MutableStateFlow<UiState<ChatQuestionResponse>>(UiState.Idle())
    override val uiState: StateFlow<UiState<ChatQuestionResponse>> = _uiState.asStateFlow()

    override fun tryQuestion(message: String) {
        viewModelScope.launch {
            askChatQuestionUseCase.invoke(message).collect { resultState ->
                when (resultState) {
                    is ResultState.Error -> _uiState.value =
                        UiState.Error(resultState.message.orEmpty())

                    is ResultState.Loading -> _uiState.value = UiState.Loading()
                    is ResultState.Success ->
                        resultState.data?.let {
                            _uiState.value = UiState.Success(it)
                        }
                }
            }
        }
    }

    override fun resetState() {
        _uiState.value = UiState.Idle()
    }
}