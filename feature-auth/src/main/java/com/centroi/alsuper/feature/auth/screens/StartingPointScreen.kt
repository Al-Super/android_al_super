package com.centroi.alsuper.feature.auth.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.centroi.alsuper.core.ui.AlSuperTheme
import com.centroi.alsuper.core.ui.LocalSpacing
import com.centroi.alsuper.core.ui.R
import com.centroi.alsuper.core.ui.Routes
import com.centroi.alsuper.core.ui.components.button.TransparentBlackButton

@Composable
fun StartingPointScreen(navController: NavController) {
    StartingPointScreen(
        navigateToLogin = { navController.navigate(Routes.LoginScreen.name) { launchSingleTop = true} },
        navigateToRegister = { navController.navigate(Routes.RegistrationScreen.name) { launchSingleTop = true} },
    )
}

@Composable
internal fun StartingPointScreen(
    navigateToLogin: () -> Unit = {},
    navigateToRegister: () -> Unit = {}
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.space3x),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { navigateToLogin() },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id= R.string.starting_point_sign_in)
            )
        }
        TransparentBlackButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id= R.string.starting_point_create_account)
        ) {
            navigateToRegister()
        }
    }
}

@Composable
@Preview
fun PreviewStartingPointScreen() {
    AlSuperTheme {
        StartingPointScreen()
    }
}
