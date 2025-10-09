package com.centroi.alsuper.core.data.repositories

import com.centroi.alsuper.core.data.models.contacts.ContactRequest
import com.centroi.alsuper.core.data.models.contacts.RemoteContact
import com.centroi.alsuper.core.data.services.ContactService
import com.centroi.alsuper.core.database.tables.EmergencyContact
import com.centroi.alsuper.core.database.tables.EmergencyContactDao
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import javax.inject.Inject
import kotlin.text.compareTo
import kotlin.text.get

/**
 * This  method should sync the local database with the remote server. It fetches contacts from both sources,
 * compares them, and updates each source with the latest changes.
 *
 * suspend fun syncContacts() {
val remoteResponse = contactService.getContacts()
if (!remoteResponse.isSuccessful) return

val remoteContacts = remoteResponse.body()?.payload?.contacts ?: emptyList()
val localContacts = dao.getEmergencyContactsList()

val remoteMap = remoteContacts.associateBy { it.id }
val localMap = localContacts.associateBy { it.id }

// Update local DB with remote changes
for (remote in remoteContacts) {
val local = localMap[remote.id]
if (local == null) {
dao.insertContact(remote.toLocal())
} else if (remote.updatedAt != null && (local.updatedAt == null || remote.updatedAt > local.updatedAt)) {
dao.updateContact(remote.toLocal())
}
}

// Update remote with local changes
for (local in localContacts) {
val remote = remoteMap[local.id]
if (remote == null) {
contactService.addContact(local.toRemoteRequest())
} else if (local.updatedAt != null && (remote.updatedAt == null || local.updatedAt > remote.updatedAt)) {
contactService.updateContact(local.id, local.toRemoteRequest())
}
}
} **/

interface EmergencyContactsRepository {
    fun getEmergencyContacts(): Flow<List<EmergencyContact>>
    suspend fun insertContact(contact: EmergencyContact)
    suspend fun deleteContact(contact: EmergencyContact)
    suspend fun updateContact(contact: EmergencyContact)
    suspend fun getContactById(contactId: Int): EmergencyContact?
}


class EmergencyContactsRepositoryImpl @Inject constructor(
    private val dao: EmergencyContactDao,
    retrofitNetwork: Retrofit
) : EmergencyContactsRepository {

    private val contactService: ContactService by lazy {
        retrofitNetwork.create(ContactService::class.java)
    }

    override fun getEmergencyContacts(): Flow<List<EmergencyContact>> {
        return dao.getEmergencyContacts()
    }

    override suspend fun insertContact(contact: EmergencyContact) {
        val request = ContactRequest(contact.name, contact.surname, contact.phoneNumber, contact.isMain)
        val response = contactService.addContact(request)
        if (response.isSuccessful) {
            val remote = response.body()?.payload?.contact
            if (remote != null) {
                dao.insertContact(remote.toLocal())
            }
        }
    }

    override suspend fun deleteContact(contact: EmergencyContact) {
        val response = contactService.deleteContact(contact.id)
        if (response.isSuccessful) {
            dao.deleteContact(contact)
        }
    }

    override suspend fun updateContact(contact: EmergencyContact) {
        val request = ContactRequest(contact.name, contact.surname, contact.phoneNumber, contact.isMain)
        val response = contactService.updateContact(contact.id, request)
        if (response.isSuccessful) {
            val remote = response.body()?.payload?.contact
            if (remote != null) {
                dao.updateContact(remote.toLocal())
            }
        }
    }

    override suspend fun getContactById(contactId: Int): EmergencyContact? {
        val response = contactService.getContact(contactId)
        return if (response.isSuccessful) {
            response.body()?.payload?.contact?.toLocal()
        } else {
            dao.getContactById(contactId)
        }
    }
    private fun RemoteContact.toLocal(): EmergencyContact = EmergencyContact(
        id = id,
        name = name,
        surname = lastName ?: "",
        phoneNumber = phoneNumber,
        color = if(isMain) "#FF5722FF" else "#FF9800FF", // Or map from remote if available
        isMain = isMain
    )
}

