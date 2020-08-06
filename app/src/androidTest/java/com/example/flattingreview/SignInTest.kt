package com.example.flattingreview

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Assert.*
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

    //a test to check the sign in screen opens
    @Test
    fun test_isActivityinView() {
        val activityScenario=ActivityScenario.launch(SignIn::class.java)
        onView(ViewMatchers.withId(R.id.signInTag))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
    //a test to check the viability of the login, forgot password and sign up botton
    @Test
    fun test_Signin_Button_Visability() {
        val activityScenario=ActivityScenario.launch(SignIn::class.java)
        onView(withId(R.id.loginButton))
            .check(matches(isDisplayed()))

        onView(withId(R.id.forgotPassButton))
            .check(matches(isDisplayed()))

        onView(withId(R.id.signUpButton))
            .check(matches(isDisplayed()))
    }
}

