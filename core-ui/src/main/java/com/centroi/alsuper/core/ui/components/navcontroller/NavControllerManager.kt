package com.centroi.alsuper.core.ui.components.navcontroller

import androidx.navigation.NavController
import com.centroi.alsuper.core.ui.Routes
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavControllerManager @Inject constructor() {

    private var navController : NavController? = null

    fun initializeNavController(navController: NavController) {
        if (this.navController == null) {
            this.navController = navController
        }
    }

    fun goToLoginScreen() {
        navController?.let {
            it.navigate(Routes.LoginScreen.name) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    fun goToFakeCartScreen() {
        navController?.let {
            it.navigate(Routes.FakeHomeScreen.name) {
                popUpTo(it.graph.startDestinationId) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }

    fun goToConfirmationScreen() {
        navController?.let {
            it.navigate(Routes.ConfirmationScreen.name) {
                popUpTo(it.graph.startDestinationId) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }


}