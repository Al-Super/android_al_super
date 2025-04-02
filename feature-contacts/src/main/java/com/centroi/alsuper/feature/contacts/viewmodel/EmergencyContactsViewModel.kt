package com.centroi.alsuper.feature.contacts.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.centroi.alsuper.core.data.EmergencyContactsRepository
import com.centroi.alsuper.core.data.extensions.EncryptionHelper
import com.centroi.alsuper.core.database.tables.EmergencyContact
import com.centroi.alsuper.feature.contacts.MAX_RGB_COLOR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import java.security.GeneralSecurityException
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class EmergencyContactsViewModel @Inject constructor(
    private val repository: EmergencyContactsRepository
) : ViewModel() {

    // Fetch and decrypt contacts inside ViewModel
    val emergencyContacts: StateFlow<List<EmergencyContact>> = repository.getEmergencyContacts()
        .map { contacts ->
            contacts.map { contact ->
                contact.copy(
                    phoneNumber = decryptPhoneNumber(contact.phoneNumber)
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList()) // Caches & avoids recomputation

    fun insertContact(name: String, surname: String, phoneNumber: String) {
        val randomColor = generateRandomColor()
        val encryptedPhoneNumber = EncryptionHelper.encrypt(phoneNumber)

        val contact = EmergencyContact(
            id = 0,
            name = name,
            surname = surname,
            phoneNumber = encryptedPhoneNumber,
            color = randomColor,
            isMain = false
        )

        viewModelScope.launch {
            repository.insertContact(contact)
        }
    }

    fun deleteContact(contact: EmergencyContact) {
        viewModelScope.launch {
            repository.deleteContact(contact)
        }
    }

    fun updateContact(contact: EmergencyContact) {
        viewModelScope.launch {
            val encryptedPhoneNumber = EncryptionHelper.encrypt(contact.phoneNumber)

            val updatedContact = contact.copy(
                phoneNumber = encryptedPhoneNumber
            )

            repository.updateContact(updatedContact)
        }
    }

    fun loadContactById(contactId: Int, onResult: (EmergencyContact?) -> Unit) {
        viewModelScope.launch {
            val contact = repository.getContactById(contactId)
            contact?.let {
                val decryptedContact = it.copy(phoneNumber = EncryptionHelper.decrypt(it.phoneNumber))
                onResult(decryptedContact) // Pass the decrypted contact to the UI
            } ?: onResult(null)
        }
    }

    private fun decryptPhoneNumber(encryptedPhone: String): String {
        return try {
            EncryptionHelper.decrypt(encryptedPhone)
        } catch (e: GeneralSecurityException) {
            Log.e("Decrypt", "Failed to decrypt phone number", e)
            "Error"
        } catch (e: IOException) {
            Log.e("Decrypt", "IO error while decrypting phone number", e)
            "Error"
        }
    }

    private fun generateRandomColor(): String {
        val color = java.util.Random().nextInt(MAX_RGB_COLOR)
        return "#" + java.lang.String.format("%06X", color)
    }
}
