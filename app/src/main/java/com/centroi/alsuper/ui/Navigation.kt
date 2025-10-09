/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.centroi.alsuper.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.centroi.alsuper.CONTACT_ID
import com.centroi.alsuper.core.ui.Routes
import com.centroi.alsuper.feature.auth.screens.ConfirmationScreen
import com.centroi.alsuper.feature.auth.screens.LoginScreen
import com.centroi.alsuper.feature.auth.screens.RegistrationScreen
import com.centroi.alsuper.feature.landingpage.ui.LandingPageScreen
import com.centroi.alsuper.feature.chat.ChatScreen
import com.centroi.alsuper.feature.contacts.screens.AddContactScreen
import com.centroi.alsuper.feature.contacts.screens.ContactsListScreen
import com.centroi.alsuper.feature.contacts.screens.EditContactScreen
import com.centroi.alsuper.feature.fakeapp.screens.CartScreen
import com.centroi.alsuper.feature.fakeapp.screens.FakeHomeScreen
import com.centroi.alsuper.feature.information.InformationScreen
import com.centroi.alsuper.feature.profile.screens.ProfileScreen

@Composable
fun MainNavigation(
    navController: NavHostController,
    loginCallback : MutableState<Boolean>,
    shouldShowLandingPage: Boolean,
    onChangeAppTheme: MutableState<Boolean>
) {
    NavHost(navController = navController, startDestination = Routes.SplashScreen.name) {
        composable(Routes.SplashScreen.name) {
            SplashScreen(
                navController = navController,
                shouldShowLandingPage = shouldShowLandingPage
            )
        }
        composable(Routes.MainScreen.name) {
            LandingPageScreen(
                onChangeAppTheme = onChangeAppTheme,
                navController = navController
            )
        }
        composable(Routes.LoginScreen.name) {
            LoginScreen(navController = navController, loginCallback = loginCallback)
        }
        composable(Routes.RegistrationScreen.name) { RegistrationScreen() }
        composable(Routes.FakeHomeScreen.name) { FakeHomeScreen(navController) }
        composable(Routes.CartScreen.name) { CartScreen(navController) }
        composable(Routes.ProfileScreen.name) { ProfileScreen(loginCallback) }
        composable(Routes.ContactsListScreen.name) { ContactsListScreen(navController = navController) }
        composable(Routes.AddContactScreen.name) { AddContactScreen(navController = navController) }
        composable(Routes.InformationScreen.name) { InformationScreen() }
        composable(Routes.SelfDiagnosisScreen.name) { ChatScreen() }
        composable(Routes.FakeArticleScreen.name) {  }
        composable(Routes.ConfirmationScreen.name) { ConfirmationScreen(loginCallback)}
        composable("${Routes.EditContactScreen.name}/{$CONTACT_ID}") { backStackEntry ->
            val contactId = backStackEntry.arguments?.getString(CONTACT_ID)?.toIntOrNull()
            contactId?.let {
                EditContactScreen(navController = navController, contactId = it)
            }
        }
    }
}

@Composable
private fun SplashScreen(
    navController: NavHostController,
    shouldShowLandingPage: Boolean
) {
    LaunchedEffect(Unit) {
        val destination = if (shouldShowLandingPage) {
            Routes.MainScreen.name
        } else {
            Routes.LoginScreen.name
        }

        navController.navigate(destination) {
            popUpTo(Routes.SplashScreen.name) {
                inclusive = true
            }
        }
    }
}