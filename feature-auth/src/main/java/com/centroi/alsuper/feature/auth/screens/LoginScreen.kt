package com.centroi.alsuper.feature.auth.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.centroi.alsuper.core.ui.LocalSpacing
import com.centroi.alsuper.core.ui.R
import com.centroi.alsuper.core.ui.Routes
import com.centroi.alsuper.core.ui.components.editText.EmailTextField
import com.centroi.alsuper.core.ui.components.editText.PasswordTextField

@Composable
fun LoginScreen(
    navController: NavController,
    loginCallback: (Boolean) -> Unit
) {
    LoginScreen(
        navigateToRegister = { navController.navigate(Routes.RegistrationScreen.name) { launchSingleTop = true} },
        loginCallback = {
            loginCallback(it)
            navController.navigate(Routes.FakeHomeScreen.name) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    )
}

@Composable
internal fun LoginScreen(
    navigateToRegister: () -> Unit = {},
    loginCallback: (Boolean) -> Unit = {}
) {
    val spacing = LocalSpacing.current

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
        EmailTextField()
        Spacer(modifier = Modifier.padding(top = spacing.space3x))
        PasswordTextField()
        Text(
            text = stringResource(id = R.string.login_forgot_password).uppercase(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacing.space2x),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.labelSmall

        )
        Button(
            onClick = { loginCallback(true) },
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
                .clickable (
                    onClick = { navigateToRegister() }
                ),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelSmall

        )
    }
}

@Composable
@Preview
fun PreviewLoginScreen() {
    LoginScreen()
}
