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
 * A class that tests the UI of the create account screen.
 *
 * @author Nikki Meadows
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class CreateAccountTest{
    /**
     * A test to check that the visibility of the create account screen.
     *
     */
    @Test
    fun test_isActivityInView() {
        val activityScenario=ActivityScenario.launch(CreateAccount::class.java)
        onView(withId(R.id.create_account_id))
            .check(matches(isDisplayed()))
    }
    /**
     * A test to check that visibility of the buttons on the create account screen.
     *
     */
    @Test
    fun test_Visibility_of_SignUp_Button() {
        val activityScenario = ActivityScenario.launch(CreateAccount::class.java)
        onView(withId(R.id.signUpButton))
            .check(matches(isDisplayed()))
    }

    /**
     * A test to check the visibility of the text fields on the create account screen.
     *
     */
    @Test
    fun test_Check_Visibility_Text_Fields() {
        val activityScenario = ActivityScenario.launch(CreateAccount::class.java)
        onView(withId(R.id.firstNameTV))
            .check(matches(isDisplayed()))
        onView(withId(R.id.lastNameTV))
            .check(matches(isDisplayed()))
        onView(withId(R.id.email))
            .check(matches(isDisplayed()))
        onView(withId(R.id.enterPass1))
            .check(matches(isDisplayed()))
        onView(withId(R.id.Address))
            .check(matches(isDisplayed()))
//        onView(withId(R.id.check_box_landlord))
//            .check(matches(isDisplayed()))
//        onView(withId(R.id.checkBoxTenant))
//            .check(matches(isDisplayed()))
//        onView(withId(R.id.tenOrLanLo))
//            .check(matches(isDisplayed()))
    }
}
