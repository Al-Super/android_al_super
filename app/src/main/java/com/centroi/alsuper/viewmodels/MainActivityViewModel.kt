package com.centroi.alsuper.viewmodels

import androidx.lifecycle.ViewModel
import com.centroi.alsuper.feature.landingpage.ui.LandingPagePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val landingPrefs: LandingPagePreferences
): ViewModel() {

    fun shouldShowLandingPage(): Boolean {
        return !landingPrefs.shouldShowLandingPage()
    }
}
