package com.example.flattingreview

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 * A class that tests the UI of the sign in screen.
 *
 * @author Nikki Meadows
 */

@RunWith(AndroidJUnit4ClassRunner::class)
class SignInTest{

    /**
     * A test to check that the visibility of the sign in screen.
     */
    @Test
    fun test_isActivityInView() {
        val activityScenario=ActivityScenario.launch(SignIn::class.java)
        onView(withId(R.id.sign_in_id))
            .check(matches(isDisplayed()))
    }
    /**
     * A test to check that visibility of the buttons on the sign in screen.
     *
     */
    @Test
    fun test_SignIn_Button_Viability() {
        val activityScenario = ActivityScenario.launch(SignIn::class.java)
        onView(withId(R.id.loginButton))
            .check(matches(isDisplayed()))
        onView(withId(R.id.forget_password_button))
            .check(matches(isDisplayed()))
        onView(withId(R.id.signUpButton))
            .check(matches(isDisplayed()))
    }

}
