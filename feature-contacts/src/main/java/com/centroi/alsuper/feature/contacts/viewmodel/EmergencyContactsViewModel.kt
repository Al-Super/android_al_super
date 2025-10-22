package com.centroi.alsuper.feature.contacts.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.centroi.alsuper.core.data.ResultState
import com.centroi.alsuper.core.data.repositories.EmergencyContactsRepository
import com.centroi.alsuper.core.data.extensions.EncryptionHelper
import com.centroi.alsuper.core.database.tables.EmergencyContact
import com.centroi.alsuper.feature.contacts.MAX_RGB_COLOR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import java.security.GeneralSecurityException
import javax.inject.Inject

@HiltViewModel
class EmergencyContactsViewModel @Inject constructor(
    private val repository: EmergencyContactsRepository
) : ViewModel() {

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage = _snackbarMessage.asStateFlow()
    val emergencyContacts: StateFlow<List<EmergencyContact>> = repository.getEmergencyContacts()
        .map { contacts ->
            contacts.map { contact ->
                contact.copy( phoneNumber = contact.phoneNumber )
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList()) // Caches & avoids recomputation

    fun insertContact(name: String, surname: String, phoneNumber: String) {
        val randomColor = generateRandomColor()

        val contact = EmergencyContact(
            id = 0,
            name = name,
            surname = surname,
            phoneNumber = phoneNumber,
            color = randomColor,
            isMain = false
        )

        viewModelScope.launch {
            repository.insertContactSafe(contact).collect { result ->
                when (result) {
                    is ResultState.Error -> _snackbarMessage.value = "No se pudo añadir el contacto. Inténtalo de nuevo."
                    is ResultState.Success -> _snackbarMessage.value = null
                    else -> {}
                }
            }
        }
    }

    fun deleteContact(contact: EmergencyContact) {
        viewModelScope.launch {
            repository.deleteContact(contact)
        }
    }

    fun updateContact(contact: EmergencyContact) {
        viewModelScope.launch {

            repository.updateContact(contact)
        }
    }

    fun loadContactById(contactId: Int, onResult: (EmergencyContact?) -> Unit) {
        viewModelScope.launch {
            val contact = repository.getContactById(contactId)
            onResult(contact)
        }
    }

    fun clearSnackbarMessage() {
        _snackbarMessage.value = null
    }

    private fun generateRandomColor(): String {
        val color = java.util.Random().nextInt(MAX_RGB_COLOR)
        return "#" + java.lang.String.format("%06X", color)
    }
}
