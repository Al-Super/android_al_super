package com.centroi.alsuper.feature.auth.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.centroi.alsuper.core.data.ResultState
import com.centroi.alsuper.core.data.models.login.LoginRegisterResponse
import com.centroi.alsuper.core.data.models.login.LoginResponse
import com.centroi.alsuper.core.data.usecases.login.GetRegisterUseCase
import com.centroi.alsuper.core.data.usecases.login.SetConfirmationCodeUseCase
import com.centroi.alsuper.core.data.userstate.UserSessionHelper
import com.centroi.alsuper.core.ui.components.navcontroller.NavControllerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class RegistrationScreenAbstract() : ViewModel() {

    abstract fun tryRegisterUser(
        name: String,
        lastName: String,
        birthdate: String,
        phone: String,
        email: String,
        password: String
    )

    abstract fun tryConfirmUser(code: String)
    abstract fun navigateToConfirmationScreen()
    abstract fun navigateToLoginScreen()

    abstract fun navigateToFakeArticlesScreen()

    abstract fun resetState()

    abstract val uiState: StateFlow<LoginUiState<LoginRegisterResponse>>
    abstract val uiStateConfirmation: StateFlow<LoginUiState<LoginResponse>>
}

@HiltViewModel
class RegistrationScreenViewModel @Inject constructor(
    private val getRegisterUseCase: GetRegisterUseCase,
    private val setConfirmationCodeUseCase: SetConfirmationCodeUseCase,
    private val navControllerManager: NavControllerManager,
    private val userSessionHelper: UserSessionHelper,
) : RegistrationScreenAbstract() {

    private val _uiState =
        MutableStateFlow<LoginUiState<LoginRegisterResponse>>(LoginUiState.Idle())
    override val uiState: StateFlow<LoginUiState<LoginRegisterResponse>>
        get() = _uiState

    private val _uiStateConfirmation =
        MutableStateFlow<LoginUiState<LoginResponse>>(LoginUiState.Idle())
    override val uiStateConfirmation: StateFlow<LoginUiState<LoginResponse>>
        get() = _uiStateConfirmation


    override fun tryRegisterUser(
        name: String,
        lastName: String,
        birthdate: String,
        phone: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            getRegisterUseCase.invoke(
                name = name,
                lastName = lastName,
                birthdate = birthdate,
                phone = phone,
                email = email,
                password = password
            ).collect { resultState ->
                when (resultState) {
                    is ResultState.Error -> {
                        _uiState.value = LoginUiState.Error(resultState.message.orEmpty())
                    }

                    is ResultState.Loading -> {
                        _uiState.value = LoginUiState.Loading()
                    }

                    is ResultState.Success -> {
                        resultState.data?.let {
                            _uiState.value = LoginUiState.Success(it)
                            userSessionHelper.setUserID(it.payload.user.id)
                        }
                    }
                }
            }
        }
    }

    override fun tryConfirmUser(code: String) {
        viewModelScope.launch {
            val  userId = userSessionHelper.getUserId()
            setConfirmationCodeUseCase.invoke(userId, code)
                .collect { resultState ->
                    when (resultState) {
                        is ResultState.Error -> {
                            _uiStateConfirmation.value = LoginUiState.Error(resultState.message.orEmpty())
                        }

                        is ResultState.Loading -> {
                            _uiStateConfirmation.value = LoginUiState.Loading()
                        }

                        is ResultState.Success -> {
                            resultState.data?.let {
                                _uiStateConfirmation.value = LoginUiState.Success(it)
                                saveTokens(it.payload.token, it.payload.refreshToken)
                            }
                        }
                    }
                }
        }
    }

    override fun navigateToFakeArticlesScreen() {
        navControllerManager.goToFakeCartScreen()
    }

    override fun navigateToLoginScreen() {
        navControllerManager.goToLoginScreen()
    }

    override fun navigateToConfirmationScreen() {
        navControllerManager.goToConfirmationScreen()
    }

    private fun saveTokens(token: String, refreshToken: String){
        viewModelScope.launch {
            userSessionHelper.saveTokens(token, refreshToken)
        }
    }

    override fun resetState() {
        _uiState.value = LoginUiState.Idle()
    }


}