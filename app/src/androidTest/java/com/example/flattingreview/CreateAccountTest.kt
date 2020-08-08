package com.example.flattingreview/*
package com.example.flattingreview

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith

*/
/**
 * A class that tests the UI of the create account screen.
 *
 * @author Nikki Meadows
 *//*

@RunWith(AndroidJUnit4ClassRunner::class)
class CreateAccountTest{

    @Test
    fun test_isActivityInView() {
        val activityScenario=ActivityScenario.launch(CreateAccount::class.java)
        onView(ViewMatchers.withId(R.id.createAccountTag))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    //A test to check the visability of the sign up button
    @Test
    fun test_Visability_of_Signup_Button() {
        val activityScenario = ActivityScenario.launch(CreateAccount::class.java)
        onView(withId(R.id.signUpButton))
            .check(matches(isDisplayed()))
    }
}*/
