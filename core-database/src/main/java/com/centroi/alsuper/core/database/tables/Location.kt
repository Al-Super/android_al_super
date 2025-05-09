package com.centroi.alsuper.core.database.tables

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity
data class Location(
    @PrimaryKey val id: Int = 1,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: Location)

    @Query("SELECT * FROM location ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastLocation(): Location?
}
