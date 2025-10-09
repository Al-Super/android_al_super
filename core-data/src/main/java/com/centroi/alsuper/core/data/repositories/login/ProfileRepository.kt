package com.centroi.alsuper.core.data.repositories.login

import com.centroi.alsuper.core.data.ResultState
import com.centroi.alsuper.core.data.extensions.ApiCall
import com.centroi.alsuper.core.data.models.user.AuthMeResponse
import com.centroi.alsuper.core.data.services.AuthUserService
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import javax.inject.Inject

fun interface ProfileRepositoryInt {
    suspend fun getProfile(): Flow<ResultState<AuthMeResponse>>
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
}