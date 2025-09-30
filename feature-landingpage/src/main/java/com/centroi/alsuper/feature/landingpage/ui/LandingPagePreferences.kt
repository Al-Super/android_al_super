package com.centroi.alsuper.feature.landingpage.ui

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Named
import androidx.core.content.edit

class LandingPagePreferences @Inject constructor (
    @Named("SharedPreferences") private val prefs: SharedPreferences
) {

    fun shouldShowLandingPage(): Boolean {
        return prefs.getBoolean(SHOW_LANDING_PAGE_KEY, false)
    }

    fun doNotShowLandingPageAgain() {
        prefs.edit { putBoolean(SHOW_LANDING_PAGE_KEY, true) }
    }

    companion object {
        const val SHOW_LANDING_PAGE_KEY = "show_landing_page"
    }
}