package com.centroi.alsuper

import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.centroi.alsuper.core.ui.R

@Composable
fun RequestLocationPermission(
    shouldShowRationaleOverride: Boolean? = null,
    launcherOverride: ((Array<String>) -> Unit)? = null,
    onPermissionGranted: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity

    val locationPermissions = arrayOf(
        "android.permission.ACCESS_FINE_LOCATION",
        "android.permission.ACCESS_COARSE_LOCATION"
    )

    var showRationale by remember { mutableStateOf(false) }
    var shouldLaunchSystemDialog by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions["android.permission.ACCESS_FINE_LOCATION"] == true ||
                permissions["android.permission.ACCESS_COARSE_LOCATION"] == true

        if (granted) onPermissionGranted()
    }

    val effectiveLauncher = launcherOverride ?: { launcher.launch(it) }

    LaunchedEffect(Unit) {
        val fineGranted = ContextCompat.checkSelfPermission(
            context,
            locationPermissions[0]
        ) == PackageManager.PERMISSION_GRANTED
        val coarseGranted = ContextCompat.checkSelfPermission(
            context,
            locationPermissions[1]
        ) == PackageManager.PERMISSION_GRANTED

        if (!fineGranted && !coarseGranted) {

            val shouldShowSystemRationale = locationPermissions.any { perm ->
                ActivityCompat.shouldShowRequestPermissionRationale(activity ?: return@LaunchedEffect, perm)
            }
           /* val shouldShow = shouldShowRationaleOverride ?: locationPermissions.any { perm ->
                ActivityCompat.shouldShowRequestPermissionRationale(activity ?: return@LaunchedEffect, perm)
            }*/

            // 🟢 Force rationale to show unless explicitly overridden to false
            val shouldShow = shouldShowRationaleOverride ?: true

            if (shouldShow) {
                showRationale = true
            } else {
                shouldLaunchSystemDialog = true
            }
        } else {
            onPermissionGranted()
        }
    }

   /* // Automatically launch system permission dialog when needed
    if (shouldLaunchSystemDialog) {
        LaunchedEffect("launchSystemDialog") {
            shouldLaunchSystemDialog = false
            effectiveLauncher(locationPermissions)
        }
    }*/

    if (showRationale) {
        AlertDialog(
            onDismissRequest = { showRationale = false },
            confirmButton = {
                TextButton(onClick = {
                    showRationale = false
                    effectiveLauncher(locationPermissions)
                }) {
                    Text(stringResource(R.string.location_authorization_accept))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showRationale = false
                }) {
                    Text(stringResource(R.string.location_authorization_deny))
                }
            },
            title = { Text(stringResource(R.string.location_authorization_title)) },
            text = {
                Text(stringResource(R.string.location_authorization_message))
            }
        )
    }
}
