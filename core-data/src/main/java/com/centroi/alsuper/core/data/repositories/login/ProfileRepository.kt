package com.centroi.alsuper.core.data.repositories.login

import com.centroi.alsuper.core.data.ResultState
import com.centroi.alsuper.core.data.extensions.ApiCall
import com.centroi.alsuper.core.data.models.user.AuthMeResponse
import com.centroi.alsuper.core.data.models.user.ForgotPasswordRequest
import com.centroi.alsuper.core.data.models.user.ForgotPasswordResponse
import com.centroi.alsuper.core.data.models.user.RestorePasswordRequest
import com.centroi.alsuper.core.data.models.user.RestorePasswordResponse
import com.centroi.alsuper.core.data.services.AuthUserService
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import javax.inject.Inject

interface ProfileRepositoryInt {
    suspend fun getProfile(): Flow<ResultState<AuthMeResponse>>
    suspend fun forgotPassword(email: String): Flow<ResultState<ForgotPasswordResponse>>

    suspend fun restorePassword(
        userId: String,
        email: String,
        password: String,
        confirmPassword: String,
        code: String
    ): Flow<ResultState<RestorePasswordResponse>>
}

class ProfileRepository @Inject constructor(
    retrofitNetwork: Retrofit
) : ProfileRepositoryInt {

    private val authService: AuthUserService by lazy {
        retrofitNetwork.create(AuthUserService::class.java)
    }

    override suspend fun getProfile(): Flow<ResultState<AuthMeResponse>> {
        return ApiCall.safeApiCall {
            authService.authUserMe()
        }
    }

    override suspend fun forgotPassword(email: String): Flow<ResultState<ForgotPasswordResponse>> {
        return ApiCall.safeApiCall {
            authService.forgotPassword(ForgotPasswordRequest(email))
        }
    }

    override suspend fun restorePassword(
        userId: String,
        email: String,
        password: String,
        confirmPassword: String,
        code: String
    ): Flow<ResultState<RestorePasswordResponse>> {
        return ApiCall.safeApiCall {
            authService.restorePassword(
                RestorePasswordRequest(userId, email, password, confirmPassword, code)
            )
        }
    }
}