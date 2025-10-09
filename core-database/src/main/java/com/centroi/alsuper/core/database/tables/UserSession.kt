package com.centroi.alsuper.core.database.tables

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity
data class UserSession(
    @PrimaryKey val id: Int = 1,
    val encryptedToken: String,
    val encryptedRefreshToken: String,
)

@Dao
interface UserSessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSession(session: UserSession)

    @Query("SELECT * FROM UserSession WHERE id = 1 LIMIT 1")
    suspend fun getSession(): UserSession?

    @Query("DELETE FROM UserSession WHERE id = 1")
    suspend fun deleteSession()
}