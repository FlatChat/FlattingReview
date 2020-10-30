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
 * A class that tests the UI of the delete account screen.
 *
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class DeleteAccountTest{

    /**
     * A test to check that the visibility of the delete account screen.
     *
     */
    @Test
    fun test_isActivityInView() {
        val activityScenario= ActivityScenario.launch(DeleteAccount::class.java)
        onView(withId(R.id.delete_account_id))
            .check(matches(isDisplayed()))
    }

    /**
     * A test to check that visibility of the buttons on the delete account screen
     *
     */
    @Test
    fun test_Visibility_Of_Delete_Buttons() {
        val activityScenario=ActivityScenario.launch(DeleteAccount::class.java)
        onView(withId(R.id.confirmDelete))
            .check(matches(isDisplayed()))
    }
}