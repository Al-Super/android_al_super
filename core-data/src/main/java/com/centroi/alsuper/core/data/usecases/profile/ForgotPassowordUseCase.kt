package com.centroi.alsuper.core.data.usecases.profile

import com.centroi.alsuper.core.data.repositories.login.ProfileRepositoryInt
import javax.inject.Inject

class ForgotPassowordUseCase @Inject constructor(
    private val repository: ProfileRepositoryInt
) {
    suspend operator fun invoke(email: String) = repository.forgotPassword(email = email)

}