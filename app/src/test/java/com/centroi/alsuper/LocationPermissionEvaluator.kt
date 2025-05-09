package com.centroi.alsuper

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class LocationPermissionEvaluator(private val context: Context) {

    fun isLocationGranted(): Boolean {
        val fine = ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION")
        val coarse = ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION")
        return fine == PackageManager.PERMISSION_GRANTED || coarse == PackageManager.PERMISSION_GRANTED
    }

    fun shouldShowRationale(): Boolean {
        val activity = context as? Activity ?: return false
        return ActivityCompat.shouldShowRequestPermissionRationale(
            activity,
            "android.permission.ACCESS_FINE_LOCATION"
        ) ||
        ActivityCompat.shouldShowRequestPermissionRationale(
            activity,
            "android.permission.ACCESS_COARSE_LOCATION"
        )
    }
}
