package com.centroi.alsuper.core.worker

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ForceKaptModule {
    @Provides
    fun force(): Boolean = true
}
