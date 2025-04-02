package com.centroi.alsuper.feature.contacts.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.centroi.alsuper.core.database.tables.EmergencyContact
import com.centroi.alsuper.core.ui.AlSuperTheme
import com.centroi.alsuper.core.ui.Dimens
import com.centroi.alsuper.core.ui.LocalFontWeight
import com.centroi.alsuper.core.ui.LocalSpacing
import com.centroi.alsuper.core.ui.components.container.AlSuperCircularContainer
import com.centroi.alsuper.core.ui.R
import com.centroi.alsuper.core.ui.Routes
import com.centroi.alsuper.core.ui.toColor
import com.centroi.alsuper.feature.contacts.viewmodel.EmergencyContactsViewModel

@Composable
fun ContactsListScreen(
    navController: NavController,
    emergencyContactsViewModel: EmergencyContactsViewModel = hiltViewModel()
){
    // Observe emergency contacts from ViewModel
    val contacts by emergencyContactsViewModel.emergencyContacts.collectAsState(initial = emptyList())

    ContactsListScreen(
        navigateToAddContact = {
            navController.navigate(Routes.AddContactScreen.name) { launchSingleTop = true}
        },
        navigateToEditContact = { contactId ->
            navController.navigate("${Routes.EditContactScreen.name}/$contactId") { launchSingleTop = true }
        },
        deleteContact = { contact ->
            emergencyContactsViewModel.deleteContact(contact)
        },
        contacts = contacts
    )
}

@Composable
internal fun ContactsListScreen(
    navigateToAddContact: () -> Unit = {},
    navigateToEditContact: (Int) -> Unit = {},
    deleteContact: (EmergencyContact) -> Unit = {},
    contacts: List<EmergencyContact>
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.space3x),
        verticalArrangement = Arrangement.spacedBy(spacing.space3x)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = stringResource(R.string.contact_list_title),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = stringResource(R.string.contact_list_add_contact),
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.clickable {
                    navigateToAddContact()
                }
            )
        }

        Text(
            text = stringResource(R.string.contact_list_description),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelSmall,
        )

        // Contacts list
        Column {
            contacts.forEach { contact ->
                ContactItem(
                    contact = contact,
                    spacing = spacing,
                    onEdit = {
                        navigateToEditContact(it.id)
                    },
                    onDelete = {
                        deleteContact(it)
                    }
                )
            }
        }
    }
}

@Composable
fun ContactItem(
    contact: EmergencyContact,
    spacing: Dimens,
    onEdit: (EmergencyContact) -> Unit,
    onDelete: (EmergencyContact) -> Unit
) {
    val weight = LocalFontWeight.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = spacing.space2x
            ),
        verticalArrangement = Arrangement.spacedBy(spacing.space2x)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AlSuperCircularContainer(
                initials = "${contact.name.first()}",
                size = spacing.space7x,
                backgroundColor = contact.color.toColor()
            )

            Spacer(modifier = Modifier.padding(spacing.space3x))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${contact.name} ${contact.surname}",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleSmall,
                )
                Text(
                    text = contact.phoneNumber,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight(weight.weight300x)
                    )
                )
            }

            /*Icon(
                painter = painterResource(id = R.drawable.ic_more_vert),
                contentDescription = "Edit contact",
                modifier = Modifier.padding(start = spacing.space3x),
                tint = MaterialTheme.colorScheme.onSurface
            )*/
            MoreContactActionsMenu(
                spacing = spacing,
                onEdit = { onEdit(contact) },
                onDelete = { onDelete(contact) }
            )
        }

        Spacer(
            modifier = Modifier
                .height(spacing.space05x)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.outline)
        )
    }
}

@Composable
fun MoreContactActionsMenu(
    spacing: Dimens,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    // More actions icon with dropdown menu
    Box {
        Icon(
            painter = painterResource(id = R.drawable.ic_more_vert),
            contentDescription = "More actions",
            modifier = Modifier
                .padding(start = spacing.space3x)
                .clickable { expanded = true },
            tint = MaterialTheme.colorScheme.onSurface
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Edit") },
                onClick = {
                    expanded = false
                    onEdit()
                }
            )
            DropdownMenuItem(
                text = { Text("Delete") },
                onClick = {
                    expanded = false
                    onDelete()
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewContactsListScreen() {
    AlSuperTheme(
        isFakeApp = false
    ) {
        ContactsListScreen(
            contacts = contacts
        )
    }
}

val contacts = listOf(
    EmergencyContact(0, "John", "Doe", "123456789", "#FFFFFF"),
    EmergencyContact(1, "Jane", "Doe", "987654321", "#FFFFFF", true)
)
