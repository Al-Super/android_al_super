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

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import com.centroi.alsuper.core.ui.R
import com.centroi.alsuper.core.ui.Routes
import com.centroi.alsuper.core.ui.components.navigation.bottomNavigation.AlSuperBottomNavigationBar
import com.centroi.alsuper.core.ui.components.navigation.AlSuperTopNavigationBar
import com.centroi.alsuper.core.common.location.LocationWorkManager
import com.centroi.alsuper.core.ui.AlSuperTheme
import com.centroi.alsuper.core.ui.components.navcontroller.NavControllerManager
import com.centroi.alsuper.viewmodels.MainActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navControllerManager: NavControllerManager

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen(
                viewModel = viewModel,
                navControllerManager = navControllerManager
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainActivityViewModel,
    navControllerManager: NavControllerManager
) {
    val navController = rememberNavController()
    navControllerManager.initializeNavController(navController)
    val context = LocalContext.current
    val signedIn = remember { mutableStateOf(false) }
    val onFakeApp = remember { mutableStateOf(true) }
    val onShowSoS = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    AlSuperTheme(
        isFakeApp = onFakeApp.value,
    ) {
        Scaffold(
            topBar = {
                if (signedIn.value) {
                    AlSuperTopNavigationBar(
                        navController = navController,
                        onMainNavigation = { onFakeApp.value = it },
                    )
                }
            },
            bottomBar = {
                if (signedIn.value) {
                    val currentRoute =
                        navController.currentBackStackEntryAsState().value?.destination?.route
                            ?: Routes.FakeHomeScreen.name
                    AlSuperBottomNavigationBar(
                        navController = navController,
                        selectedRoute = currentRoute,
                        onFakeApp = onFakeApp.value
                    )
                }
            }
        ) { innerPadding ->

            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                MainNavigation(
                    navController = navController,
                    loginCallback = signedIn,
                    shouldShowLandingPage = viewModel.shouldShowLandingPage(),
                    onChangeAppTheme = onFakeApp
                )
                when {
                    navController.currentDestination?.route == Routes.InformationScreen.name ||
                    navController.currentDestination?.route == Routes.SelfDiagnosisScreen .name ||
                    navController.currentDestination?.route == Routes.ContactsListScreen.name ||
                    (navController.currentDestination?.route == Routes.ProfileScreen.name && !onFakeApp.value)-> {
                        Box(
                            modifier = Modifier
                                .padding(32.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.secondary)
                                .align(Alignment.BottomCenter)
                                .padding(20.dp)
                                .clickable(onClick = { onShowSoS.value = true})

                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.ic_sos),
                                    contentDescription = "Panic Button",
                                    colorFilter = ColorFilter.tint(color = Color.DarkGray)
                                )
                                Text(
                                    modifier = Modifier.padding(start = 8.dp),
                                    text = "Panic button",
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.W500
                                )
                            }
                        }
                    }
                }
                if(onShowSoS.value){
                    ModalBottomSheet(
                        sheetState = sheetState,
                        onDismissRequest = {
                            onShowSoS.value = false
                        },
                        modifier = Modifier.fillMaxSize(),
                        containerColor = MaterialTheme.colorScheme.background,
                    ) {
                        PanicScreen(onShowSoS)
                    }
                }

            }
            Log.d("MainScreen", "MainScreen: $innerPadding")
        }
    }

    //TODO put this behind an if that validates that user is logged in
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            LocationWorkManager.ensureScheduled(context)
        }
    }
}
