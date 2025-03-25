package com.centroi.alsuper.core.data

import com.centroi.alsuper.core.database.tables.EmergencyContact
import com.centroi.alsuper.core.database.tables.EmergencyContactDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface EmergencyContactsRepository {
    fun getEmergencyContacts(): Flow<List<EmergencyContact>>
    suspend fun insertContact(contact: EmergencyContact)
    suspend fun deleteContact(contact: EmergencyContact)
    suspend fun updateContact(contact: EmergencyContact)
    suspend fun getContactById(contactId: Int): EmergencyContact?
}


class EmergencyContactsRepositoryImpl @Inject constructor(private val dao: EmergencyContactDao) : EmergencyContactsRepository {
    override fun getEmergencyContacts(): Flow<List<EmergencyContact>> {
        return dao.getEmergencyContacts()
    }

    override suspend fun insertContact(contact: EmergencyContact) {
        withContext(Dispatchers.IO) {
            dao.insertContact(contact)
        }
    }

    override suspend fun deleteContact(contact: EmergencyContact) {
        withContext(Dispatchers.IO) {
            dao.deleteContact(contact)
        }
    }

    override suspend fun updateContact(contact: EmergencyContact) {
        dao.updateContact(contact)
    }

    override suspend fun getContactById(contactId: Int): EmergencyContact? {
        return dao.getContactById(contactId)
    }
}