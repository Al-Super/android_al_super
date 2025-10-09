package com.centroi.alsuper.core.data.network

import okhttp3.OkHttpClient
import javax.inject.Inject

interface HttpInterceptorInterface {
    fun provide(): OkHttpClient
}

class HttpInterceptor @Inject constructor(
    private val tokenInterceptor: TokenInterceptor
) : HttpInterceptorInterface {
    override fun provide(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .build()
    }


}