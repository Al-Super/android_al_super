package com.centroi.alsuper.core.worker

import com.centroi.alsuper.core.data.repositories.LocationRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface LocationWorkerEntryPoint {
    fun locationRepository(): LocationRepository
}
