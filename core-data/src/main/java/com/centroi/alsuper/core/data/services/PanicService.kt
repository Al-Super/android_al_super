package com.centroi.alsuper.core.data.services

import com.centroi.alsuper.core.data.models.panic.PanicResponse
import retrofit2.Response
import retrofit2.http.POST

interface PanicService {
    @POST("/api/sos")
    suspend fun sendSos(): Response<PanicResponse>
}