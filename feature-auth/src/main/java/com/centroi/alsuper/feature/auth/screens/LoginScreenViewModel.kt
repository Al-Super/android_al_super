package com.centroi.alsuper.feature.auth.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.centroi.alsuper.core.data.ResultState
import com.centroi.alsuper.core.data.models.login.LoginResponse
import com.centroi.alsuper.core.data.usecases.login.GetLoginAuthUseCase
import com.centroi.alsuper.core.data.userstate.UserSessionHelper
import com.centroi.alsuper.core.ui.components.UiState
import com.centroi.alsuper.core.ui.components.navcontroller.NavControllerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class LoginScreenViewModelAbstract() : ViewModel() {

    abstract fun tryLogIn(email: String, password: String)
    abstract fun goToFakeCartScreen()

    abstract fun resetState()
    abstract fun shouldGoToFakeCartScreen(callback: (Boolean) -> Unit)

    abstract val uiState: StateFlow<UiState<LoginResponse>>
}

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val getLoginUseCase: GetLoginAuthUseCase,
    private val navControllerManager: NavControllerManager,
    private val userSessionHelper: UserSessionHelper
) : LoginScreenViewModelAbstract() {

    private val _uiState = MutableStateFlow<UiState<LoginResponse>>(UiState.Idle())
    override val uiState: StateFlow<UiState<LoginResponse>> = _uiState.asStateFlow()

    override fun goToFakeCartScreen() {
        navControllerManager.goToFakeCartScreen()
    }

    override fun tryLogIn(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            getLoginUseCase.invoke(email, password).collect { resultState ->
                when (resultState) {
                    is ResultState.Error -> {
                        _uiState.value = UiState.Error(resultState.message.orEmpty())
                    }
                    is ResultState.Loading -> {
                        _uiState.value = UiState.Loading()
                    }
                    is ResultState.Success -> {
                        resultState.data?.let {
                            _uiState.value = UiState.Success(it)
                            saveTokens(it.payload.token, it.payload.refreshToken)
                        }
                    }
                }
            }
        }
    }

    override fun shouldGoToFakeCartScreen(callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            callback.invoke(userSessionHelper.isUserLoggedIn())
        }
    }

    private fun saveTokens(token: String, refreshToken: String){
        viewModelScope.launch {
            userSessionHelper.saveTokens(token, refreshToken)
        }
    }

    override fun resetState() {
        _uiState.value = UiState.Idle()
    }


    private fun validateCredentials(
        email: String,
        password: String
    ): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }


}