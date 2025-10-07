package com.centroi.alsuper.feature.auth.screens

import androidx.lifecycle.ViewModel
import javax.inject.Inject

abstract class LoginScreenViewModelAbstract() : ViewModel() {

    abstract fun tryLogIn(email: String, password: String, loginCallback: (Boolean) -> Unit)
}

class LoginScreenViewModel @Inject constructor() : LoginScreenViewModelAbstract() {

    override fun tryLogIn(
        email: String,
        password: String,
        loginCallback: (Boolean) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    private fun validateCredentials(
        email: String,
        password: String
    ): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }


}