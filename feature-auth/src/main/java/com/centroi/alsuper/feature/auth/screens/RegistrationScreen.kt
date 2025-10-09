package com.centroi.alsuper.feature.auth.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.centroi.alsuper.core.ui.LocalSpacing
import com.centroi.alsuper.core.ui.R
import com.centroi.alsuper.core.ui.components.UiState
import com.centroi.alsuper.core.ui.components.editText.DateSelectionTextField
import com.centroi.alsuper.core.ui.components.editText.EmailTextField
import com.centroi.alsuper.core.ui.components.editText.NameTextField
import com.centroi.alsuper.core.ui.components.editText.PasswordTextField
import com.centroi.alsuper.core.ui.components.editText.PhoneNumberTextField

@Composable
fun RegistrationScreen(
    viewModel: RegistrationScreenAbstract = hiltViewModel<RegistrationScreenViewModel>()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val dimens = LocalSpacing.current

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is UiState.Success -> {
                viewModel.navigateToConfirmationScreen()
                viewModel.resetState()
            }

            is UiState.Error -> {
                snackbarHostState.showSnackbar(message = state.message.orEmpty())
                viewModel.resetState()
            }

            else -> {}
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier.padding(bottom = dimens.space6x),
                hostState = snackbarHostState
            )
        }
    ) { _ ->
        RegistrationScreen(
            navigateToLogin = {
                viewModel.navigateToLoginScreen()
            },
            viewModel = viewModel
        )
    }
}

@Composable
internal fun RegistrationScreen(
    navigateToLogin: () -> Unit = {},
    viewModel: RegistrationScreenAbstract
) {
    val spacing = LocalSpacing.current
    val name = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.space3x),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.registration_sign_up),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = spacing.space3x),
            textAlign = TextAlign.Start

        )
        NameTextField(
            onValueChange = { name.value = it },
            labelText = stringResource(id = R.string.registration_first_name),
            placeholderText = stringResource(id = R.string.registration_first_name_placeholder)
        )
        Spacer(modifier = Modifier.padding(top = spacing.space3x))
        NameTextField(
            onValueChange = { lastName.value = it },
            labelText = stringResource(id = R.string.registration_last_name),
            placeholderText = stringResource(id = R.string.registration_last_name_placeholder)
        )
        Spacer(modifier = Modifier.padding(top = spacing.space3x))
        DateSelectionTextField(
            date = date
        )
        Spacer(modifier = Modifier.padding(top = spacing.space3x))
        PhoneNumberTextField(
            onValueChange = { phone.value = it },
        )
        Spacer(modifier = Modifier.padding(top = spacing.space3x))
        EmailTextField(email)
        Spacer(modifier = Modifier.padding(top = spacing.space3x))
        PasswordTextField(password)
        Button(
            onClick = {
                viewModel.tryRegisterUser(
                    name = name.value,
                    lastName = lastName.value,
                    birthdate = date.value,
                    phone = phone.value,
                    email = email.value,
                    password = password.value,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacing.space6x)
        ) {
            Text(
                text = stringResource(id = R.string.registration_create_account)
            )
        }
        Text(
            text = stringResource(id = R.string.registration_already_in).uppercase(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacing.space6x)
                .clickable(onClick = navigateToLogin),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelSmall

        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewRegistrationScreen() {
    RegistrationScreen()
}
