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
import com.centroi.alsuper.core.ui.components.editText.DateSelectionTextField
import com.centroi.alsuper.core.ui.components.editText.EmailTextField
import com.centroi.alsuper.core.ui.components.editText.NameTextField
import com.centroi.alsuper.core.ui.components.editText.PasswordTextField
import com.centroi.alsuper.core.ui.components.editText.PhoneNumberTextField

@Composable
fun RegistrationScreen(navController: NavController) {
    RegistrationScreen(navigateToLogin = { navController.navigate(Routes.LoginScreen.name) { launchSingleTop = true} })
}

@Composable
internal fun RegistrationScreen(
    navigateToLogin: () -> Unit = {}
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
            text = stringResource(id = R.string.registration_sign_up),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = spacing.space3x),
            textAlign = TextAlign.Start

        )
        NameTextField(
            labelText = stringResource(id = R.string.registration_first_name),
            placeholderText = stringResource(id = R.string.registration_first_name_placeholder)
        )
        Spacer(modifier = Modifier.padding(top = spacing.space3x))
        NameTextField(
            labelText = stringResource(id = R.string.registration_last_name),
            placeholderText = stringResource(id = R.string.registration_last_name_placeholder)
        )
        Spacer(modifier = Modifier.padding(top = spacing.space3x))
        DateSelectionTextField()
        Spacer(modifier = Modifier.padding(top = spacing.space3x))
        PhoneNumberTextField()
        Spacer(modifier = Modifier.padding(top = spacing.space3x))
        EmailTextField()
        Spacer(modifier = Modifier.padding(top = spacing.space3x))
        PasswordTextField()
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacing.space6x)
        ) {
            Text(
                text = stringResource(id= R.string.registration_create_account)
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
@Preview
fun PreviewRegistrationScreen() {
    RegistrationScreen()
}