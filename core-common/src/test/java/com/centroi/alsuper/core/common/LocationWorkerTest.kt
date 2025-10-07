package com.centroi.alsuper.core.common

import android.content.Context
import android.location.Location
import androidx.work.ListenableWorker.Result
import androidx.work.testing.TestListenableWorkerBuilder
import com.centroi.alsuper.core.data.repositories.LocationRepository
import com.centroi.alsuper.core.common.location.LocationProvider
import com.centroi.alsuper.core.common.location.LocationWorker
import com.centroi.alsuper.core.common.location.LocationWorkerEntryPoint
import dagger.hilt.EntryPoints
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.assertEquals
import org.mockito.kotlin.any
import org.mockito.MockedStatic
import org.mockito.Mockito.mock
import org.mockito.Mockito.mockStatic
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.eq

class LocationWorkerTest {

    private lateinit var context: Context
    private lateinit var repository: LocationRepository
    private lateinit var locationProvider: LocationProvider

    @Before
    fun setup() {
        context = mock(Context::class.java)
        repository = mock(LocationRepository::class.java)
        locationProvider = mock(LocationProvider::class.java)
    }

    @Test
    fun `doWork saves location and returns success`() = runTest {
        val fakeLocation = mock(Location::class.java)
        `when`(fakeLocation.latitude).thenReturn(10.0)
        `when`(fakeLocation.longitude).thenReturn(20.0)

        `when`(locationProvider.getLastKnownLocation()).thenReturn(fakeLocation)

        val entryPoint = mock(LocationWorkerEntryPoint::class.java).apply {
            `when`(locationProvider()).thenReturn(locationProvider)
            `when`(locationRepository()).thenReturn(repository)
        }

        mockStatic(EntryPoints::class.java).use { staticMock: MockedStatic<EntryPoints> ->
            `when`(
                EntryPoints.get(
                    any(),
                    eq(LocationWorkerEntryPoint::class.java)
                )
            ).thenReturn(entryPoint)

            val worker = TestListenableWorkerBuilder<LocationWorker>(context).build()

            val result = worker.doWork()

            assertEquals(Result.success(), result)
            verify(repository).saveLocation(10.0, 20.0)
        }
    }

    @Test
    fun `doWork returns success when location is null`() = runTest {
        `when`(locationProvider.getLastKnownLocation()).thenReturn(null)

        val entryPoint = mock(LocationWorkerEntryPoint::class.java).apply {
            `when`(locationProvider()).thenReturn(locationProvider)
            `when`(locationRepository()).thenReturn(repository)
        }

        mockStatic(EntryPoints::class.java).use { staticMock: MockedStatic<EntryPoints> ->
            `when`(
                EntryPoints.get(
                    any(),
                    eq(LocationWorkerEntryPoint::class.java)
                )
            ).thenReturn(entryPoint)

            val worker = TestListenableWorkerBuilder<LocationWorker>(context).build()

            val result = worker.doWork()

            assertEquals(Result.success(), result)
            verify(repository, never()).saveLocation(any(), any())
        }
    }
}




