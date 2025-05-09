package com.centroi.alsuper.core.worker.location

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.EntryPoints

@HiltWorker
class LocationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val entryPoint = EntryPoints.get(
            applicationContext,
            LocationWorkerEntryPoint::class.java
        )

        val location = entryPoint.locationProvider().getLastKnownLocation()
        location?.let {
            entryPoint.locationRepository().saveLocation(it.latitude, it.longitude)
        }

        return Result.success()
    }
}

