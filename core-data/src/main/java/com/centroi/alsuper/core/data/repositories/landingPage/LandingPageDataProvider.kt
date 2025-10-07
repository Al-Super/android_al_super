package com.centroi.alsuper.core.data.repositories.landingPage

import android.content.Context
import com.centroi.alsuper.core.data.R
import com.centroi.alsuper.core.data.models.LandingPageData
import com.centroi.alsuper.core.data.models.LandingPageName
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

fun interface LandingPageDataProviderInt {
    fun getPages(): List<LandingPageData>
}

class LandingPageDataProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LandingPageDataProviderInt {

    @Suppress("LongMethod")
    override fun getPages(): List<LandingPageData> = listOf(
        LandingPageData(
            pageID = LandingPageName.LANDING_PAGE_FIRST,
            title = context.getString(R.string.LANDING_PAGE_ONE_TITLE),
            description = context.getString(R.string.LANDING_PAGE_ONE_DESCRIPTION)
        ),
        LandingPageData(
            pageID = LandingPageName.LANDING_PAGE_SECOND,
            title = context.getString(R.string.LANDING_PAGE_TWO_TITLE),
            description = context.getString(R.string.LANDING_PAGE_TWO_DESCRIPTION),
            text = context.getString(R.string.LANDING_PAGE_TWO_TEXT)
        ),
        LandingPageData(
            pageID = LandingPageName.LANDING_PAGE_THIRD,
            title = context.getString(R.string.LANDING_PAGE_THREE_TITLE),
            description = context.getString(R.string.LANDING_PAGE_THREE_DESCRIPTION)
        ),
        LandingPageData(
            pageID = LandingPageName.LANDING_PAGE_FOURTH,
            title = context.getString(R.string.LANDING_PAGE_FOUR_TITLE),
            description = context.getString(R.string.LANDING_PAGE_FOUR_DESCRIPTION)
        ),
        LandingPageData(
            pageID = LandingPageName.LANDING_PAGE_FIFTH,
            buttonClose = context.getString(R.string.LANDING_PAGE_CLOSE_BUTTON),
            title = context.getString(R.string.LANDING_PAGE_FIVE_TITLE),
            description = context.getString(R.string.LANDING_PAGE_FIVE_DESCRIPTION)
        ),
        LandingPageData(
            pageID = LandingPageName.LANDING_PAGE_SIXTH,
            title = context.getString(R.string.LANDING_PAGE_SIX_TITLE),
            description = context.getString(R.string.LANDING_PAGE_SIX_DESCRIPTION),
            checkBoxTermsAndConditions = context.getString(R.string.LANDING_PAGE_SIX_TERMS_AND_CONDITIONS),
            checkBoxDataConsent = context.getString(R.string.LANDING_PAGE_SIX_DATA_CONSENT),
            consentNeeded = true
        )
    )
}
