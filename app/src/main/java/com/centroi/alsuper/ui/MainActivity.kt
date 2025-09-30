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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import com.centroi.alsuper.core.ui.AlSuperTheme
import com.centroi.alsuper.core.ui.Routes
import com.centroi.alsuper.core.ui.components.navigation.bottomNavigation.AlSuperBottomNavigationBar
import com.centroi.alsuper.core.ui.components.navigation.AlSuperTopNavigationBar
import com.centroi.alsuper.core.worker.location.LocationWorkManager
import com.centroi.alsuper.viewmodels.MainActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen(
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun MainScreen(
    viewModel: MainActivityViewModel
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val signedIn = remember { mutableStateOf(false) }
    val onFakeApp = remember { mutableStateOf(true) }

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

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                MainNavigation(
                    navController = navController,
                    loginCallback = { signedIn.value = it },
                    shouldShowLandingPage = viewModel.shouldShowLandingPage(),
                    onChangeAppTheme = onFakeApp
                )
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
