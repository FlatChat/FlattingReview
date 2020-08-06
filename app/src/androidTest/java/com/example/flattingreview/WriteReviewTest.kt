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
class WriteReviewTest{
    @Test
    fun test_isActivityInView() {
        val activityScenario=ActivityScenario.launch(WriteReview::class.java)
        onView(ViewMatchers.withId(R.id.writeReviewTag))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    //a test to check the cancel and submit buttons are visible
    @Test
    fun test_Visibility_WriteReviewScreen_Buttons() {
        val activityScenario=ActivityScenario.launch(WriteReview::class.java)

        onView(ViewMatchers.withId(R.id.cancel))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withId(R.id.submit_button))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}