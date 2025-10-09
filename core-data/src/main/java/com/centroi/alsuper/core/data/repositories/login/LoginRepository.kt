package com.centroi.alsuper.core.data.repositories.login

import com.centroi.alsuper.core.data.ResultState
import com.centroi.alsuper.core.data.extensions.ApiCall
import com.centroi.alsuper.core.data.models.login.LoginConfirmationRequest
import com.centroi.alsuper.core.data.models.login.LoginRegisterRequest
import com.centroi.alsuper.core.data.models.login.LoginRegisterResponse
import com.centroi.alsuper.core.data.models.login.LoginRequest
import com.centroi.alsuper.core.data.models.login.LoginResponse
import com.centroi.alsuper.core.data.services.AuthUserService
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import javax.inject.Inject

interface LoginRepositoryInt {
    fun fetchLogin(email: String, password: String): Flow<ResultState<LoginResponse>>
    fun fetchRegister(name: String, lastName: String, birthdate: String, phone: String, email: String, password: String): Flow<ResultState<LoginRegisterResponse>>

    fun fetchConfirmation(userId: String, code: String): Flow<ResultState<LoginResponse>>
}


class LoginRepository @Inject constructor(
    retrofitNetwork: Retrofit
): LoginRepositoryInt {

    private val loginService: AuthUserService by lazy {
        retrofitNetwork.create(AuthUserService::class.java)
    }

    override fun fetchLogin(
        email: String,
        password: String
    ): Flow<ResultState<LoginResponse>> {
        return ApiCall.safeApiCall {
            loginService.loginService(LoginRequest(email, password))
        }
    }

    override fun fetchRegister(
        name: String,
        lastName: String,
        birthdate: String,
        phone: String,
        email: String,
        password: String
    ): Flow<ResultState<LoginRegisterResponse>> {
        return ApiCall.safeApiCall {
            loginService
                .registerService(
                    LoginRegisterRequest(
                        name = name,
                        lastName = lastName,
                        email = email,
                        password = password,
                        phoneNumber = phone,
                        birthdate = null
                    )
                )
        }
    }

    override fun fetchConfirmation(
        userId: String,
        code: String
    ): Flow<ResultState<LoginResponse>> {
        return ApiCall.safeApiCall {
            loginService
                .loginConfirmationService(
                    LoginConfirmationRequest(
                        userId = userId,
                        code = code
                    )
                )
        }
    }

}