package com.example.flattingreview

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

    @Test
    fun test_isActivityInView() {
       // val activityScenario=ActivityScenario.launch(ChangePassword::class.java)
        onView(withId(R.id.changePasswordTag)).check(matches(isDisplayed()))
    }

    //a test to check the visabilty of the change password button
    @Test
    fun test_Visability_of_ChangePassword_Button() {
        val activityScenario=ActivityScenario.launch(ChangePassword::class.java)
        onView(withId(R.id.changePasBut))
            .check(matches(isDisplayed()))
    }
}
