package com.example.flattingreview

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Assert.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 * TODO
 *
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class SplashScreenTest{

    //checks if the activity is launched
    @Test
    fun test_isActivityInView() {
        val activityScenario=ActivityScenario.launch(SplashScreen::class.java)
        onView(withId(R.id.splashTag)).check(matches(isDisplayed()))
    }
}