package com.centroi.alsuper.feature.auth.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.centroi.alsuper.core.ui.components.editText.EmailTextField
import com.centroi.alsuper.core.ui.components.editText.PasswordTextField

@Composable
fun LoginScreen(
    navController: NavController,
    loginCallback: MutableState<Boolean>,
    viewModel: LoginScreenViewModelAbstract = hiltViewModel<LoginScreenViewModel>()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val dimens = LocalSpacing.current

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is LoginUiState.Success -> {
                loginCallback.value = true
                viewModel.goToFakeCartScreen()
                viewModel.resetState()
            }
            is LoginUiState.Error -> {
                snackbarHostState.showSnackbar(message = state.message.orEmpty())
                viewModel.resetState()
            }
            else -> { }
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
        LoginScreen(
            navigateToRegister = { navController.navigate(Routes.RegistrationScreen.name) { launchSingleTop = true} },
            viewModel = viewModel
        )
    }
}

@Composable
internal fun LoginScreen(
    navigateToRegister: () -> Unit = {},
    viewModel: LoginScreenViewModelAbstract
) {
    val spacing = LocalSpacing.current
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    Box {
        Image(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = spacing.space10x)
            ,
            painter = painterResource(R.drawable.ic_light_logo),
            contentDescription = ""
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
                    .padding(top = spacing.space2x),
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
                    text = stringResource(id= R.string.starting_point_sign_in)
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
    LoginScreen(rememberNavController(),remember { mutableStateOf(false) })
}
