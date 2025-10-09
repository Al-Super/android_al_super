package com.centroi.alsuper.core.database.tables

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity
data class UserID(
    @PrimaryKey val id: Int = 1,
    val userId: String,
)

@Dao
interface UserIDDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserID(session: UserID)

    @Query("SELECT * FROM UserID WHERE id = 1 LIMIT 1")
    suspend fun getUserID(): UserID?
}
