package com.centroi.alsuper.feature.contacts.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.centroi.alsuper.core.database.tables.EmergencyContact
import com.centroi.alsuper.core.ui.Dimens
import com.centroi.alsuper.core.ui.LocalSpacing
import com.centroi.alsuper.core.ui.MainYellow
import com.centroi.alsuper.core.ui.R
import com.centroi.alsuper.core.ui.YellowLightGray
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
    contactId: Int? = null,
    viewModel: EmergencyContactsViewModel = hiltViewModel(),
    saveContact: () -> Unit = {}
) {
    val spacing = LocalSpacing.current
    val formState = rememberContactState(contactId, viewModel)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.space3x)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 72.dp), // Leaves space for the button
            verticalArrangement = Arrangement.spacedBy(spacing.space3x)
        ) {
            item {
                ContactFormContent(contactId, formState, spacing)
            }
        }

        SaveContactButton(
            contactId = contactId,
            viewModel = viewModel,
            formState = formState,
            saveContact = saveContact,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = spacing.space6x)
                .fillMaxWidth()
                .heightIn(min = 56.dp)
        )
    }
}

@Composable
private fun rememberContactState(
    contactId: Int?,
    viewModel: EmergencyContactsViewModel
): ContactFormState {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }

    LaunchedEffect(contactId) {
        contactId?.let {
            viewModel.loadContactById(it) { existingContact ->
                existingContact?.let { contact ->
                    name = contact.name
                    surname = contact.surname
                    phoneNumber = contact.phoneNumber
                    color = contact.color
                }
            }
        }
    }

    return ContactFormState(
        name = name,
        surname = surname,
        phoneNumber = phoneNumber,
        color = color,
        onNameChange = { name = it },
        onSurnameChange = { surname = it },
        onPhoneNumberChange = { phoneNumber = it }
    )
}

data class ContactFormState(
    val name: String,
    val surname: String,
    val phoneNumber: String,
    val color: String,
    val onNameChange: (String) -> Unit,
    val onSurnameChange: (String) -> Unit,
    val onPhoneNumberChange: (String) -> Unit,
)

@Composable
private fun ContactFormContent(
    contactId: Int?,
    formState: ContactFormState,
    spacing: Dimens
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.space3x)
    ) {
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
            labelText = stringResource(R.string.registration_first_name),
            placeholderText = stringResource(R.string.add_contact_first_name_placeholder),
            onValueChange = formState.onNameChange,
            defaultValue = formState.name
        )

        NameTextField(
            labelText = stringResource(R.string.registration_last_name),
            placeholderText = stringResource(R.string.add_contact_last_name_placeholder),
            onValueChange = formState.onSurnameChange,
            defaultValue = formState.surname
        )

        PhoneNumberTextField(
            placeholder = stringResource(R.string.add_contact_phone_placeholder),
            defaultValue = formState.phoneNumber,
            onValueChange = formState.onPhoneNumberChange
        )

        Text(
            text = stringResource(R.string.add_contact_legend),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Thin),
        )
    }
}

@Composable
private fun SaveContactButton(
    contactId: Int?,
    viewModel: EmergencyContactsViewModel,
    formState: ContactFormState,
    saveContact: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        colors = ButtonColors(
            containerColor = MainYellow,
            contentColor = Color.Black,
            disabledContentColor = Color.White,
            disabledContainerColor = YellowLightGray,
        ),
        onClick = {
            if (contactId == null) {
                viewModel.insertContact(formState.name, formState.surname, formState.phoneNumber)
            } else {
                viewModel.updateContact(
                    EmergencyContact(
                        id = contactId,
                        name = formState.name,
                        surname = formState.surname,
                        phoneNumber = formState.phoneNumber,
                        color = formState.color
                    )
                )
            }
            saveContact()
        },
        modifier = modifier
    ) {
        Text(text = stringResource(R.string.add_contact_save_button))
    }
}
