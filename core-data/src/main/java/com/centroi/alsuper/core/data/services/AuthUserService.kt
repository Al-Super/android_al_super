package com.centroi.alsuper.core.data.services

import com.centroi.alsuper.core.data.models.login.LoginConfirmationRequest
import com.centroi.alsuper.core.data.models.login.LoginRefreshTokenRequest
import com.centroi.alsuper.core.data.models.login.LoginRefreshTokenResponse
import com.centroi.alsuper.core.data.models.login.LoginRegisterRequest
import com.centroi.alsuper.core.data.models.login.LoginRegisterResponse
import com.centroi.alsuper.core.data.models.login.LoginRequest
import com.centroi.alsuper.core.data.models.login.LoginResponse
import com.centroi.alsuper.core.data.models.user.AuthMeResponse
import com.centroi.alsuper.core.data.models.user.ForgotPasswordRequest
import com.centroi.alsuper.core.data.models.user.ForgotPasswordResponse
import com.centroi.alsuper.core.data.models.user.RestorePasswordRequest
import com.centroi.alsuper.core.data.models.user.RestorePasswordResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthUserService {

    @POST("/api/auth/register")
    suspend fun registerService(@Body loginRegisterRequest: LoginRegisterRequest): Response<LoginRegisterResponse>

    @POST("/api/auth/login")
    suspend fun loginService(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/api/auth/refresh")
    suspend fun refreshTokenService(@Body loginRequest: LoginRefreshTokenRequest): Response<LoginRefreshTokenResponse>

    @PUT("/api/auth/activate")
    suspend fun loginConfirmationService(@Body loginRequest: LoginConfirmationRequest): Response<LoginResponse>

    @GET("/api/auth/me")
    suspend fun authUserMe(): Response<AuthMeResponse>

    @POST("/api/password/forgot")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): Response<ForgotPasswordResponse>

    @PUT("/api/password/restore")
    suspend fun restorePassword(@Body request: RestorePasswordRequest): Response<RestorePasswordResponse>
}