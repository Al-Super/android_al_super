package com.centroi.alsuper

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test


class RequestPermissionsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun dialog_shows_and_triggers_permission_request() {
        var launched = false

        composeTestRule.setContent {
            // fake permission check: simulate permission not granted
            RequestLocationPermission(
                onPermissionGranted = {},
                shouldShowRationaleOverride = true, // if you parameterize it for test
                launcherOverride = { permissions ->
                    // simulate user clicking "Allow" on the dialog
                    launched = true
                }
            )
        }

        composeTestRule.onNodeWithText("Allow us to use your location").assertExists()
        composeTestRule.onNodeWithText("Allow").performClick()

        assertTrue(launched)
    }
}