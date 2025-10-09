package com.centroi.alsuper.core.common.location

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.centroi.alsuper.core.common.WORK_TIME
import java.util.concurrent.TimeUnit

object LocationWorkManager {
    private const val UNIQUE_WORK_NAME = "location_tracking"

    fun ensureScheduled(context: Context) {
        val workManager = WorkManager.getInstance(context)
        workManager.getWorkInfosForUniqueWork(UNIQUE_WORK_NAME).get().let { workInfos ->
            val alreadyScheduled = workInfos.any {
                it.state == androidx.work.WorkInfo.State.ENQUEUED ||
                        it.state == androidx.work.WorkInfo.State.RUNNING
            }

           if (!alreadyScheduled) {
                schedule(context)
           }
        }
    }

    fun schedule(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = PeriodicWorkRequestBuilder<LocationWorker>(WORK_TIME.toLong(), TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            UNIQUE_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    fun cancel(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(UNIQUE_WORK_NAME)
    }
}
