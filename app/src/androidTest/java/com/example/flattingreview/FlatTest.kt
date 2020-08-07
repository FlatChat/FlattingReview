package com.example.flattingreview

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 * TODO
 *
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class FlatTest{

    @Test
    fun test_isActivityInView() {
        val activityScenario=ActivityScenario.launch(Flat::class.java)
        onView(ViewMatchers.withId(R.id.flatTag))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    //A test to check the visibility of the buttons on the flat screen
    @Test
    fun test_Visibility_Review_Buttons() {
        val activityScenario=ActivityScenario.launch(Flat::class.java)
        onView(ViewMatchers.withId(R.id.show_reviews_button))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withId(R.id.add_review_button))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}