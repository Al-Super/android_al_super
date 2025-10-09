package com.centroi.alsuper.core.data.usecases.login

import com.centroi.alsuper.core.data.repositories.login.LoginRepositoryInt
import javax.inject.Inject

class GetLoginAuthUseCase @Inject constructor(
    private val repository: LoginRepositoryInt
) {
    operator fun invoke(email: String, password: String) =
        repository.fetchLogin(email, password)
}