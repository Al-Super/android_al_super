package com.centroi.alsuper.feature.profile.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.centroi.alsuper.core.data.ResultState
import com.centroi.alsuper.core.data.models.user.AuthMeResponse
import com.centroi.alsuper.core.data.usecases.profile.ForgotPassowordUseCase
import com.centroi.alsuper.core.data.usecases.profile.GetProfileUseCase
import com.centroi.alsuper.core.data.usecases.profile.RestorePasswordUseCase
import com.centroi.alsuper.core.data.userstate.UserSessionHelper
import com.centroi.alsuper.core.ui.components.UiState
import com.centroi.alsuper.core.ui.components.navcontroller.NavControllerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


abstract class ProfileScreenViewModelAbstract : ViewModel() {
    abstract fun loadProfile()
    abstract fun closeSession()
    abstract fun resetState()
    abstract fun forgotPassword()
    abstract fun closeDialog()
    abstract fun clearSnackbarMessage()
    abstract fun restorePassword(password: String, confirmPassword: String, code: String)
    abstract val uiState: StateFlow<UiState<AuthMeResponse>>
    abstract val snackbarMessage: StateFlow<String?>
    abstract val showRestoreDialog: StateFlow<Boolean>
}

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val forgotPassowordUseCase: ForgotPassowordUseCase,
    private val restorePasswordUseCase: RestorePasswordUseCase,
    private val userSessionHelper: UserSessionHelper,
    private val navController: NavControllerManager
) : ProfileScreenViewModelAbstract() {

    private val _uiState = MutableStateFlow<UiState<AuthMeResponse>>(UiState.Idle())
    override val uiState: StateFlow<UiState<AuthMeResponse>> = _uiState.asStateFlow()
    private val _snackbarMessage = MutableStateFlow<String?>(null)
    override val snackbarMessage = _snackbarMessage.asStateFlow()
    private val _showRestoreDialog = MutableStateFlow(false)
    override val showRestoreDialog = _showRestoreDialog.asStateFlow()
    private var userIdForRestore: String? = null
    private var userEmailForRestore: String? = null


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
                            userIdForRestore = it.payload.user?.id
                            userEmailForRestore = it.payload.user?.email
                        }
                }
            }
        }
    }

    override fun forgotPassword() {
        viewModelScope.launch {
            forgotPassowordUseCase.invoke(userEmailForRestore.orEmpty()).collect { result ->
                when (result) {
                    is ResultState.Success -> {
                        userIdForRestore = result.data?.payload?.activationCode?.userId
                        _showRestoreDialog.value = true
                        _snackbarMessage.value = "Se ha enviado un código de activación"
                    }
                    is ResultState.Error -> _snackbarMessage.value = result.message
                    else -> {}
                }
            }
        }
    }

    override fun restorePassword(
        password: String,
        confirmPassword: String,
        code: String
    ) {
        viewModelScope.launch {
            restorePasswordUseCase.invoke(userIdForRestore.orEmpty(), userEmailForRestore.orEmpty(), password, confirmPassword, code).collect { result ->
                when (result) {
                    is ResultState.Success -> {
                        _snackbarMessage.value = "Contraseña restaurada correctamente"
                        _showRestoreDialog.value = false
                        delay(2000)
                        closeSession()
                    }
                    is ResultState.Error -> _snackbarMessage.value = result.message
                    else -> {}
                }
            }
        }
    }

    override fun closeDialog() {
        _showRestoreDialog.value = false
        clearSnackbarMessage()
    }

    override fun clearSnackbarMessage() {
        _snackbarMessage.value = null
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