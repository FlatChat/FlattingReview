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
 * A class that tests the UI of the create flat screen.
 *
 *@author Nikki Meadows
 */

@RunWith(AndroidJUnit4ClassRunner::class)
class CreateFlatTest{
    /**
     * A test to check that the visibility of the create a flat screen.
     *
     */
    @Test
    fun test_isActivityInView() {
        val activityScenario=ActivityScenario.launch(CreateFlat::class.java)
        onView(withId(R.id.create_flat_id))
            .check(matches(isDisplayed()))
    }
    /**
     * A test to check that visibility of the buttons on the create a flat screen.
     *
     */
    @Test
    fun test_Visibility_Create_Button() {
        val activityScenario=ActivityScenario.launch(CreateFlat::class.java)
        onView(withId(R.id.create_flat))
            .check(matches(isDisplayed()))
        onView(withId(R.id.upload_image))
            .check(matches(isDisplayed()))
    }
}
