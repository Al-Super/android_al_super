package com.centroi.alsuper.core.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

enum class Routes {
    MainScreen,
    StartingPointScreen,
    LoginScreen,
    RegistrationScreen,
    FakeHomeScreen,
    CartScreen,
    ProfileScreen,
    InformationScreen,
    SelfDiagnosisScreen,
    ContactsListScreen,
    AddContactScreen,
    EditContactScreen,
    SplashScreen,
    FakeArticleScreen
}

@Suppress("MaxLineLength")
sealed class BottomNavItem(val route: String, @StringRes val labelResId: Int, @DrawableRes val iconResId: Int) {
    object FakeHome : BottomNavItem(Routes.FakeHomeScreen.name, R.string.bottom_bar_home, R.drawable.ic_home)
    object Cart : BottomNavItem(Routes.CartScreen.name, R.string.bottom_bar_cart, R.drawable.ic_basket)
    object Profile : BottomNavItem(Routes.ProfileScreen.name, R.string.bottom_bar_profile, R.drawable.ic_account)
    object Information : BottomNavItem(Routes.InformationScreen.name, R.string.bottom_bar_information, R.drawable.ic_article)
    object SelfDiagnosis : BottomNavItem(Routes.SelfDiagnosisScreen.name, R.string.bottom_bar_diagnosis, R.drawable.ic_diagnosis)
    object ContactsList : BottomNavItem(Routes.ContactsListScreen.name, R.string.bottom_bar_contacts, R.drawable.ic_contacts)
}

val fakeBottomNavigation = listOf(
    BottomNavItem.FakeHome,
    BottomNavItem.Cart,
    BottomNavItem.Profile
)

val mainBottomNavigation = listOf(
    BottomNavItem.Information,
    BottomNavItem.SelfDiagnosis,
    BottomNavItem.ContactsList,
    BottomNavItem.Profile
)
