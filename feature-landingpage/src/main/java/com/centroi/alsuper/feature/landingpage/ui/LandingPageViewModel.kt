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

package com.centroi.alsuper.feature.landingpage.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.centroi.alsuper.core.data.LandingPageRepository
import com.centroi.alsuper.feature.landingpage.ui.LandingPageUiState.Error
import com.centroi.alsuper.feature.landingpage.ui.LandingPageUiState.Loading
import com.centroi.alsuper.feature.landingpage.ui.LandingPageUiState.Success
import javax.inject.Inject

private const val TIME = 5000

@HiltViewModel
class LandingPageViewModel @Inject constructor(
    private val landingPageRepository: LandingPageRepository
) : ViewModel() {

    val uiState: StateFlow<LandingPageUiState> = landingPageRepository
        .landingPages.map<List<String>, LandingPageUiState> { Success(data = it) }
        .catch { emit(Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(TIME.toLong()), Loading)

    fun addLandingPage(name: String) {
        viewModelScope.launch {
            landingPageRepository.add(name)
        }
    }
}

sealed interface LandingPageUiState {
    object Loading : LandingPageUiState
    data class Error(val throwable: Throwable) : LandingPageUiState
    data class Success(val data: List<String>) : LandingPageUiState
}
