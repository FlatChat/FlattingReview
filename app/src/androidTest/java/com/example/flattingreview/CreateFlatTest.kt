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
class CreateFlatTest{
    @Test
    fun test_isActivityInView() {
        val activityScenario=ActivityScenario.launch(CreateFlat::class.java)
        onView(ViewMatchers.withId(R.id.create_flat_tag))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    //A test to check the visibility of the create a flat button
    @Test
    fun test_Visibility_Create_Button() {
        val activityScenario=ActivityScenario.launch(CreateFlat::class.java)
        onView(ViewMatchers.withId(R.id.create_flat))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}