package com.centroi.alsuper.core.worker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.android.gms.location.LocationServices
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.suspendCancellableCoroutine

@HiltWorker
class LocationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        val fusedClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        val permission = ActivityCompat.checkSelfPermission(
            applicationContext, Manifest.permission.ACCESS_FINE_LOCATION
        )

        //if (permission != PackageManager.PERMISSION_GRANTED) return Result.failure()
        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.w("LocationWorker", "Location permission not granted.")
            return Result.success() // Don't block future jobs
        }

        /*val location = suspendCancellableCoroutine<Location?> { cont ->
            fusedClient.lastLocation
                .addOnSuccessListener { cont.resume(it, null) }
                .addOnFailureListener { cont.resume(null, null) }
        }*/

        val location = try {
            suspendCancellableCoroutine<Location?> { cont ->
                fusedClient.lastLocation
                    .addOnSuccessListener { cont.resume(it, null) }
                    .addOnFailureListener {
                        Log.e("LocationWorker", "Failed to get location", it)
                        cont.resume(null, null)
                    }
            }
        } catch (e: Exception) {
            Log.e("LocationWorker", "Exception fetching location", e)
            return Result.success() // Retry not necessary for transient issue
        }

        location?.let {
            // Send lat/lon to backend
            Log.d("LocationWorker", "Location: ${it.latitude}, ${it.longitude}")
        }

        return Result.success()
    }
}
