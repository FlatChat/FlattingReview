package com.example.flattingreview

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.junit.Assert.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 * TODO
 *
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class SettingsTest{

    @Test
    fun test_isActivityInView() {
        val activityScenario=ActivityScenario.launch(Settings::class.java)
        onView(ViewMatchers.withId(R.id.settingsTag))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    //a test to check the visibility of the change password and logout buttons
    @Test
    fun test_Visibility_SettingsScreen_Buttons() {
        val activityScenario=ActivityScenario.launch(Settings::class.java)
        onView(ViewMatchers.withId(R.id.changePassBut))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withId(R.id.logoutBut))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}