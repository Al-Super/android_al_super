package com.centroi.alsuper.core.database.tables

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity
data class EmergencyContact(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val surname: String,
    val phoneNumber: String,
    val color: String,
    val isMain: Boolean = false
)

@Dao
interface EmergencyContactDao {
    @Query("SELECT * FROM emergencyContact ORDER BY id")
    fun getEmergencyContacts(): Flow<List<EmergencyContact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: EmergencyContact)

    @Delete
    suspend fun deleteContact(contact: EmergencyContact)

    @Update
    suspend fun updateContact(contact: EmergencyContact)

    @Query("SELECT * FROM emergencyContact WHERE id = :contactId")
    suspend fun getContactById(contactId: Int): EmergencyContact?
}
