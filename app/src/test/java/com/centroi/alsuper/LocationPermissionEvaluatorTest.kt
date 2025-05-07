package com.centroi.alsuper

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.centroi.alsuper.LocationPermissionEvaluator
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LocationPermissionEvaluatorTest {

    private lateinit var context: Context
    private lateinit var activity: Activity

    @Before
    fun setUp() {
        context = mock(Activity::class.java)
        //activity = context as Activity
        activity = Robolectric.buildActivity(Activity::class.java).setup().get()
    }

    @Test
    fun `isLocationGranted returns true when fine location granted`() {
        `when`(ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION"))
            .thenReturn(PackageManager.PERMISSION_GRANTED)

        `when`(ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION"))
            .thenReturn(PackageManager.PERMISSION_DENIED)

        val evaluator = LocationPermissionEvaluator(context)
        assertTrue(evaluator.isLocationGranted())
    }

    @Test
    fun `shouldShowRationale returns false by default`() {
        val evaluator = LocationPermissionEvaluator(activity)
        assertFalse(evaluator.shouldShowRationale()) // because Robolectric doesn't simulate rationale
    }
}