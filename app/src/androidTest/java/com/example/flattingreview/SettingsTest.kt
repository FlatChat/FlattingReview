package com.example.flattingreview

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 * A class that tests the UI of the settings screen.
 *
 */

@RunWith(AndroidJUnit4ClassRunner::class)
class SettingsTest{

    /**
     * A test to check that the visibility of the settings screen.
     *
     */
    @Test
    fun test_isActivityInView() {
        val activityScenario=ActivityScenario.launch(Settings::class.java)
        onView(withId(R.id.settings_screen_id))
            .check(matches(isDisplayed()))
    }
    /**
     * A test to check that visibility of the buttons on the settings screen.
     *
     */
    @Test
    fun test_Visibility_SettingsScreen_Buttons() {
        val activityScenario=ActivityScenario.launch(Settings::class.java)
        onView(withId(R.id.changePassBut))
            .check(matches(isDisplayed()))
        onView(withId(R.id.deleteAccountButton))
            .check(matches(isDisplayed()))
        onView(withId(R.id.logoutBut))
            .check(matches(isDisplayed()))
    }

    /**
     * A test to check the buttons navigate to the correct activities.
     *
     */
    @Test
    fun test_Buttons_Function() {
        val activityScenario=ActivityScenario.launch(Settings::class.java)
        onView(withId(R.id.changePassBut)).perform(click())
        onView(withId(R.id.change_password_id))
            .check(matches(isDisplayed()))

         pressBack()

        onView(withId(R.id.deleteAccountButton)).perform(click())
        onView(withId(R.id.delete_account_id))
            .check(matches(isDisplayed()))

        pressBack()
        onView(withId(R.id.logoutBut)).perform(click())
        onView(withId(R.id.sign_in_id))
            .check(matches(isDisplayed()))

    }
}
