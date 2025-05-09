package com.centroi.alsuper.core.data.repositories

import com.centroi.alsuper.core.database.tables.Location
import com.centroi.alsuper.core.database.tables.LocationDao
import javax.inject.Inject

interface LocationRepository {
    suspend fun saveLocation(lat: Double, lon: Double)
    suspend fun getLastKnown(): Location?
}

class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao
) : LocationRepository {
    override suspend fun saveLocation(lat: Double, lon: Double) {
        locationDao.insertLog(Location(latitude = lat, longitude = lon))
    }

    override suspend fun getLastKnown(): Location? = locationDao.getLastLocation()
}
