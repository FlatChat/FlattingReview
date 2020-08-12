package com.example.flattingreview

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
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

    /**
     * A test to check the visibility of the text fields on the sign in screen.
     *
     */
    @Test
    fun test_Check_Text_Fields() {
        val activityScenario = ActivityScenario.launch(SignIn::class.java)
        onView(withId(R.id.enter_email))
            .check(matches(isDisplayed()))
        onView(withId(R.id.enter_password))
            .check(matches(isDisplayed()))

    }

    /**
     * A test to check that a user cannot login without an existing account.
     *
     */
    @Test
    fun test_Check_Input() {
        val activityScenario=ActivityScenario.launch(SignIn::class.java)
        onView(withId(R.id.enter_email)).perform(typeText("testing3456@gmail.com"))
        onView(withId(R.id.enter_password)).perform(typeText("0123"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withId(R.id.sign_in_id))
            .check(matches(isDisplayed()))
    }

    /**
     * A test to check that the sign up button takes the user to the create account screen.
     *
     */
    @Test
    fun test_Check_Button_Takes_User_SignUp_Screen() {
        val activityScenario=ActivityScenario.launch(SignIn::class.java)
        onView(withId(R.id.signUpButton)).perform(click())
        onView(withId(R.id.create_account_id))
            .check(matches(isDisplayed()))
    }

    /**
     * A test to check that the forgot password dialog pops up for the user.
     *
     */
    @Test
    fun test_Check_ForgotPassword_Dialog() {
        val activityScenario=ActivityScenario.launch(SignIn::class.java)
        onView(withId(R.id.forget_password_button)).perform(click())
        onView(withId(R.id.et_username))
            .check(matches(isDisplayed()))

    }
}
