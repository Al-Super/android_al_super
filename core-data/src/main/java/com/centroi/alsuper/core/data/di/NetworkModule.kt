package com.centroi.alsuper.core.data.di

import com.centroi.alsuper.core.data.network.TokenInterceptor
import com.centroi.alsuper.core.data.services.AuthUserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // 1. Provide a dedicated OkHttpClient for the refresh token call
    //    This client does NOT have the TokenInterceptor.
    @Provides
    @Singleton
    @Named("RefreshOkHttpClient")
    fun provideRefreshOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    // 2. Provide a Retrofit instance that uses the refresh OkHttpClient
    @Provides
    @Singleton
    @Named("RefreshRetrofit")
    fun provideRefreshRetrofit(
        @Named("RefreshOkHttpClient") okHttpClient: OkHttpClient
        // You might need to inject your base URL and converter factory here as well
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api-ai-super-245879661756.northamerica-south1.run.app") // Replace with your actual base URL
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 3. This is the provider that fixes the error.
    //    It provides an AuthUserService specifically for refreshing the token.
    @Provides
    @Singleton
    @Named("refresh")
    fun provideRefreshAuthUserService(
        @Named("RefreshRetrofit") retrofit: Retrofit
    ): AuthUserService {
        return retrofit.create(AuthUserService::class.java)
    }

    // 4. Your existing OkHttpClient provider for regular API calls
    @Provides
    @Singleton
    fun provideOkHttpClient(
        tokenInterceptor: TokenInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .build()
    }

    // 5. Your existing Retrofit provider for regular API calls
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
        // You might need to inject your base URL and converter factory here as well
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api-ai-super-245879661756.northamerica-south1.run.app") // Replace with your actual base URL
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 6. Your existing provider for the standard AuthUserService
    @Provides
    @Singleton
    fun provideAuthUserService(retrofit: Retrofit): AuthUserService {
        return retrofit.create(AuthUserService::class.java)
    }
}