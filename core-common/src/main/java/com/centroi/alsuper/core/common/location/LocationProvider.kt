package com.centroi.alsuper.core.common.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.suspendCancellableCoroutine

interface LocationProvider {
    suspend fun getLastKnownLocation(): android.location.Location??
}

class DefaultLocationProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : LocationProvider {
    override suspend fun getLastKnownLocation(): android.location.Location? {
        val permission = ActivityCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            return null
        }

        val fusedClient = LocationServices.getFusedLocationProviderClient(context)

        return suspendCancellableCoroutine<android.location.Location?> { cont ->
            fusedClient.lastLocation
                .addOnSuccessListener { cont.resume(it, null) }
                .addOnFailureListener {
                    Log.e("LocationWorker", "Failed to get location", it)
                    cont.resume(null, null)
                }
        }
    }
}
