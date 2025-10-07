package com.centroi.alsuper.core.ui.components.navcontroller

import androidx.navigation.NavController
import com.centroi.alsuper.core.ui.Routes
import javax.inject.Inject

interface NavControllerManagerInterface {

    fun initializeNavController(navController: NavController)

    fun goToFakeCartScreen()


}

class NavControllerManager @Inject constructor(): NavControllerManagerInterface {

    private var navController : NavController? = null

    override fun initializeNavController(navController: NavController) {
        if (this.navController == null) {
            this.navController = navController
        }
    }

    override fun goToFakeCartScreen() {
        navController?.let {
            it.navigate(Routes.FakeHomeScreen.name) {
                popUpTo(it.graph.startDestinationId) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }


}