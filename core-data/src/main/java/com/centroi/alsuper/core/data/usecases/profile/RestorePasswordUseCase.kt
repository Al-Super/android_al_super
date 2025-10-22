package com.centroi.alsuper.core.data.usecases.profile

import com.centroi.alsuper.core.data.repositories.login.ProfileRepositoryInt
import javax.inject.Inject

class RestorePasswordUseCase @Inject constructor(
    private val repository: ProfileRepositoryInt
) {
    suspend operator fun invoke(
        userId: String,
        email: String,
        password: String,
        confirmPassword: String,
        code: String
    ) = repository.restorePassword(
        userId = userId,
        email = email,
        password = password,
        confirmPassword = confirmPassword,
        code = code
    )

}