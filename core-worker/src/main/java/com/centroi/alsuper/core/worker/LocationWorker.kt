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
import com.centroi.alsuper.core.data.repositories.EmergencyContactsRepository
import com.centroi.alsuper.core.data.repositories.LocationRepository
import com.google.android.gms.location.LocationServices
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.EntryPointAccessors
import jakarta.inject.Inject
import kotlinx.coroutines.suspendCancellableCoroutine

@HiltWorker
class LocationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val fusedClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        val repo = EntryPointAccessors.fromApplication(
            applicationContext,
            LocationWorkerEntryPoint::class.java
        ).locationRepository()

        val result = if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.w("LocationWorker", "Location permission not granted.")
            Result.success()
        } else {
            val location = try {
                suspendCancellableCoroutine<Location?> { cont ->
                    fusedClient.lastLocation
                        .addOnSuccessListener { cont.resume(it, null) }
                        .addOnFailureListener {
                            Log.e("LocationWorker", "Failed to get location", it)
                            cont.resume(null, null)
                        }
                }
            } catch (e: SecurityException) {
                Log.e("LocationWorker", "Location permission missing", e)
                null
            } catch (e: IllegalStateException) {
                Log.e("LocationWorker", "Location service unavailable", e)
                null
            }

            location?.let {
                Log.d("LocationWorker", "Location: ${it.latitude}, ${it.longitude}")
                repo.saveLocation(it.latitude, it.longitude)
            }

            Result.success()
        }

        return result
    }
}
