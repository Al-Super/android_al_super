package com.centroi.alsuper.core.data.models

data class LandingPageData(
    val pageID: LandingPageName,
    val buttonClose: String? = null,
    val title: String?,
    val description: String?,
    val text: String? = null,
    val checkBoxTermsAndConditions: String? = null,
    val checkBoxDataConsent: String? = null,
    val consentNeeded: Boolean = false,
)

enum class LandingPageName {
    LANDING_PAGE_FIRST,
    LANDING_PAGE_SECOND,
    LANDING_PAGE_THIRD,
    LANDING_PAGE_FOURTH,
    LANDING_PAGE_FIFTH,
    LANDING_PAGE_SIXTH,
}
