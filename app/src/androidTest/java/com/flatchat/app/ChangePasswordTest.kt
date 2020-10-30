package com.flatchat.app

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 * A class that tests the UI of the change password screen.
 *
 * @author Nikki Meadows
 */

@RunWith(AndroidJUnit4ClassRunner::class)
class ChangePasswordTest{
    /**
     * A test to check that the visibility of the change password screen.
     *
     */
    @Test
    fun test_isActivityInView() {
        val activityScenario= ActivityScenario.launch(ChangePassword::class.java)
        onView(withId(R.id.change_password_id)).check(matches(isDisplayed()))
    }
    /**
     * A test to check that visibility of the buttons on the change password screen.
     *
     */
    @Test
    fun test_Visibility_of_ChangePassword_Button() {
        val activityScenario=ActivityScenario.launch(ChangePassword::class.java)
        onView(withId(R.id.changePasBut))
            .check(matches(isDisplayed()))
    }

    /**
     * A test to check the visibility of the text fields on the change password screen.
     *
     */
    @Test
    fun test_Check_Text_Fields() {
        val activityScenario=ActivityScenario.launch(ChangePassword::class.java)
        onView(withId(R.id.currentPassET))
            .check(matches(isDisplayed()))
        onView(withId(R.id.newPassET))
            .check(matches(isDisplayed()))
        onView(withId(R.id.confirmPassET))
            .check(matches(isDisplayed()))
    }
}
