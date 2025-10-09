package com.centroi.alsuper.feature.profile.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.centroi.alsuper.core.data.ResultState
import com.centroi.alsuper.core.data.models.user.AuthMeResponse
import com.centroi.alsuper.core.data.usecases.profile.GetProfileUseCase
import com.centroi.alsuper.core.data.userstate.UserSessionHelper
import com.centroi.alsuper.core.ui.components.UiState
import com.centroi.alsuper.core.ui.components.navcontroller.NavControllerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


abstract class ProfileScreenViewModelAbstract : ViewModel() {
    abstract fun loadProfile()
    abstract fun closeSession()
    abstract fun resetState()
    abstract val uiState: StateFlow<UiState<AuthMeResponse>>
}

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val userSessionHelper: UserSessionHelper,
    private val navController: NavControllerManager
) : ProfileScreenViewModelAbstract() {

    private val _uiState = MutableStateFlow<UiState<AuthMeResponse>>(UiState.Idle())
    override val uiState: StateFlow<UiState<AuthMeResponse>> = _uiState.asStateFlow()

    override fun loadProfile() {
        viewModelScope.launch {
            getProfileUseCase.invoke().collect { resultState ->
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


    override fun closeSession() {
        viewModelScope.launch {
            userSessionHelper.closeSession()
        }
        navController.goToLoginScreen()
    }

    override fun resetState() {
        _uiState.value = UiState.Idle()
    }
}