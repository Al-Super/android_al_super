package com.centroi.alsuper.core.data.usecases.login

import com.centroi.alsuper.core.data.repositories.login.LoginRepositoryInt
import javax.inject.Inject

class GetRegisterUseCase @Inject constructor(
    private val repository: LoginRepositoryInt
) {
    operator fun invoke(
        name: String,
        lastName: String,
        birthdate: String,
        phone: String,
        email: String,
        password: String
    ) =
        repository.fetchRegister(
            name = name,
            lastName = lastName,
            birthdate = birthdate,
            phone = phone,
            email = email,
            password = password
        )
}