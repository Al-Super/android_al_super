package com.centroi.alsuper.core.data.usecases.login

import com.centroi.alsuper.core.data.repositories.login.LoginRepositoryInt
import javax.inject.Inject

class SetConfirmationCodeUseCase @Inject constructor (
    private val repository: LoginRepositoryInt
) {
    operator fun invoke(
        userId: String,
        code: String
    ) = repository.fetchConfirmation(userId, code)
}