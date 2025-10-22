package com.centroi.alsuper.core.data.repositories.panic

import com.centroi.alsuper.core.data.ResultState
import com.centroi.alsuper.core.data.extensions.ApiCall
import com.centroi.alsuper.core.data.models.panic.PanicResponse
import com.centroi.alsuper.core.data.services.PanicService
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import javax.inject.Inject

interface PanicRepositoryInt {
    fun sendSos(): Flow<ResultState<PanicResponse>>
}

class PanicRepository @Inject constructor(
    retrofitNetwork: Retrofit
) : PanicRepositoryInt {

    private val sosService: PanicService by lazy {
        retrofitNetwork.create(PanicService::class.java)
    }

    override fun sendSos(): Flow<ResultState<PanicResponse>> {
        return ApiCall.safeApiCall {
            sosService.sendSos()
        }
    }
}