package com.centroi.alsuper.feature.auth.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.centroi.alsuper.core.ui.LocalSpacing
import com.centroi.alsuper.core.ui.R
import com.centroi.alsuper.core.ui.Routes
import com.centroi.alsuper.core.ui.components.UiState
import com.centroi.alsuper.core.ui.components.editText.EmailTextField
import com.centroi.alsuper.core.ui.components.editText.PasswordTextField
import com.centroi.alsuper.feature.auth.screens.dialog.RestorePasswordDialog

@Composable
fun LoginScreen(
    navController: NavController,
    loginCallback: MutableState<Boolean>,
    viewModel: LoginScreenViewModelAbstract = hiltViewModel<LoginScreenViewModel>()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val dimens = LocalSpacing.current
    val isLoading = remember { mutableStateOf(false) }

    val snackbarMessage by viewModel.snackbarMessage.collectAsState()
    val showRestoreDialog by viewModel.showRestoreDialog.collectAsState()
    val showEmailDialog = remember { mutableStateOf(false) }
    val emailForReset = remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        viewModel.shouldGoToFakeCartScreen { isLoggedIn ->
            loginCallback.value = isLoggedIn
            if (isLoggedIn) {
                viewModel.goToFakeCartScreen()
            }
        }
        viewModel.resetState()
    }

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is UiState.Success -> {
                loginCallback.value = true
                viewModel.goToFakeCartScreen()
                viewModel.resetState()
            }

            is UiState.Error -> {
                snackbarHostState.showSnackbar(message = "Hubo un error al iniciar sesión")
                viewModel.resetState()
            }

            is UiState.Loading -> {
                isLoading.value = true
            }

            is UiState.Idle -> {
                isLoading.value = false
            }
        }
    }

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearSnackbarMessage()
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
        if (isLoading.value) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }
        } else {
            LoginScreen(
                navigateToRegister = {
                    navController.navigate(Routes.RegistrationScreen.name) {
                        launchSingleTop = true
                    }
                },
                viewModel = viewModel,
                onForgotPassword = { showEmailDialog.value = true }
            )
        }
        if (showEmailDialog.value) {
            var email by remember { mutableStateOf("") }
            AlertDialog(
                containerColor = MaterialTheme.colorScheme.background,
                onDismissRequest = { showEmailDialog.value = false },
                title = { Text("Reiniciar contraseña") },
                text = {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") }
                    )
                },
                confirmButton = {
                    Button(onClick = {
                        emailForReset.value = email
                        viewModel.forgotPassword(email)
                        showEmailDialog.value = false
                    }) {
                        Text("Enviar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showEmailDialog.value = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
        RestorePasswordDialog (
            showDialog = showRestoreDialog,
            onDismiss = { viewModel.closeDialog() },
            onRestorePassword = { code, password, confirmPassword ->
                viewModel.restorePassword(
                    email = emailForReset.value,
                    password = password,
                    confirmPassword = confirmPassword,
                    code = code
                )
            },
            onRetry = { viewModel.forgotPassword(emailForReset.value) }
        )
    }
}

@Composable
internal fun LoginScreen(
    navigateToRegister: () -> Unit = {},
    viewModel: LoginScreenViewModelAbstract,
    onForgotPassword: () -> Unit = {}
) {
    val spacing = LocalSpacing.current
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    Box {
        Image(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = spacing.space10x)
                .height(spacing.space16x),
            painter = painterResource(R.drawable.ic_purple_logo),
            contentDescription = "",
            contentScale = ContentScale.FillHeight
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(spacing.space3x),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.login_sign_in_to_continue),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = spacing.space3x),
                textAlign = TextAlign.Start

            )
            EmailTextField(email)
            Spacer(modifier = Modifier.padding(top = spacing.space3x))
            PasswordTextField(password)
            Text(
                text = stringResource(id = R.string.login_forgot_password).uppercase(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacing.space2x)
                    .clickable { onForgotPassword() },
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.labelSmall

            )
            Button(
                onClick = {
                    viewModel.tryLogIn(
                        email = email.value,
                        password = password.value,
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacing.space6x),
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                )
            ) {
                Text(
                    text = stringResource(id = R.string.starting_point_sign_in)
                )
            }
            Text(
                text = stringResource(id = R.string.login_new_user).uppercase(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacing.space6x)
                    .clickable(
                        onClick = { navigateToRegister() }
                    ),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall

            )
        }
    }
}


@Composable
@Preview
fun PreviewLoginScreen() {
    LoginScreen(rememberNavController(), remember { mutableStateOf(false) })
}
