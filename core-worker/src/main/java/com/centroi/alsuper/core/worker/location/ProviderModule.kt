package com.centroi.alsuper.core.worker.location

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ProviderModule {

    @Binds
    fun bindLocationProvider(
        impl: DefaultLocationProvider
    ): LocationProvider
}
