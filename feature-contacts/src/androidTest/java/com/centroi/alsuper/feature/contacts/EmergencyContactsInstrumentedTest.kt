package com.centroi.alsuper.feature.contacts

import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.centroi.alsuper.feature.contacts.viewmodel.EmergencyContactsViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class EmergencyContactsInstrumentedTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun insertContact_updatesContactsList() {
       /* val scenario = launchActivity<HiltTestActivity>()

        scenario.onActivity { activity ->
            val viewModel: EmergencyContactsViewModel = ViewModelProvider(activity)[EmergencyContactsViewModel::class.java]

            viewModel.insertContact("Jane", "Doe", "5551234567")

            val contacts = viewModel.emergencyContacts.value
            assertEquals(1, contacts.size)
            assertEquals("Jane", contacts.first().name)
        }*/
    }
}