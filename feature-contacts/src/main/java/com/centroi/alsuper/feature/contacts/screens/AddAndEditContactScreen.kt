package com.centroi.alsuper.feature.contacts.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.centroi.alsuper.core.database.tables.EmergencyContact
import com.centroi.alsuper.core.ui.LocalSpacing
import com.centroi.alsuper.core.ui.R
import com.centroi.alsuper.core.ui.components.button.MainButton
import com.centroi.alsuper.core.ui.components.editText.NameTextField
import com.centroi.alsuper.core.ui.components.editText.PhoneNumberTextField
import com.centroi.alsuper.feature.contacts.viewmodel.EmergencyContactsViewModel

@Composable
fun AddContactScreen(navController: NavController) {
    Dialog(
        onDismissRequest = { navController.popBackStack() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AddAndEditContactScreen(
                saveContact = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun EditContactScreen(navController: NavController, contactId: Int) {
    Dialog(
        onDismissRequest = { navController.popBackStack() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AddAndEditContactScreen(
                contactId = contactId,
                saveContact = { navController.popBackStack() }
            )
        }
    }
}

@Composable
internal fun AddAndEditContactScreen(
    contactId: Int? = null, // Nullable ID to determine Add/Edit mode
    viewModel: EmergencyContactsViewModel = hiltViewModel(),
    saveContact: () -> Unit = {}
) {
    val spacing = LocalSpacing.current

    var loading by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }

    // Load contact data if editing
    LaunchedEffect(contactId) {
        contactId?.let {
            viewModel.loadContactById(contactId) { existingContact ->
                existingContact?.let {
                    name = existingContact.name
                    surname = existingContact.surname
                    phoneNumber = existingContact.phoneNumber // Already decrypted in ViewModel
                    color = existingContact.color
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.space3x),
        verticalArrangement = Arrangement.spacedBy(spacing.space3x)
    ) {
        val triple = ContactInputSection(name, surname, phoneNumber)
        name = triple.first
        phoneNumber = triple.second
        surname = triple.third

        if(contactId == null) {
            MainButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = R.string.add_contact_another_button)
            ) {
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (contactId == null) {
                    // Insert New Contact
                    viewModel.insertContact(name, surname, phoneNumber)
                } else {
                    // Update Existing Contact
                    viewModel.updateContact(
                        EmergencyContact(
                            id = contactId,
                            name = name,
                            surname = surname,
                            phoneNumber = phoneNumber,
                            color = color
                        )
                    )
                }
                saveContact()
          },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id= R.string.add_contact_save_button)
            )
        }
    }
}

@Composable
private fun ContactInputSection(
    name: String,
    surname: String,
    phoneNumber: String
): Triple<String, String, String> {
    var name1 = name
    var surname1 = surname
    var phoneNumber1 = phoneNumber
    Text(
        text = stringResource(R.string.add_contact_first_title),
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.titleLarge,
    )

    Text(
        text = stringResource(R.string.add_contact_first_description),
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.labelMedium,
    )

    NameTextField(
        labelText = stringResource(id = R.string.registration_first_name),
        placeholderText = stringResource(id = R.string.add_contact_first_name_placeholder),
        onValueChange = { name1 = it },
        defaultValue = name1
    )

    NameTextField(
        labelText = stringResource(id = R.string.registration_last_name),
        placeholderText = stringResource(id = R.string.add_contact_last_name_placeholder),
        onValueChange = { surname1 = it },
        defaultValue = surname1
    )

    PhoneNumberTextField(
        placeholder = stringResource(R.string.add_contact_phone_placeholder),
        defaultValue = phoneNumber1,
        onValueChange = { phoneNumber1 = it }
    )

    Text(
        text = stringResource(R.string.add_contact_legend),
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Thin),
    )
    return Triple(name1, phoneNumber1, surname1)
}
