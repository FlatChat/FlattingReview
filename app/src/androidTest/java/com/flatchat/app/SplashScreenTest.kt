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
 * A class that tests the UI of the splash screen.
 *
 *@author Nikki Meadows
 */

@RunWith(AndroidJUnit4ClassRunner::class)
class SplashScreenTest{

    /**
     * A test to check that the visibility of the splash screen.
     *
     */
    @Test
    fun test_isActivityInView() {
        val activityScenario=ActivityScenario.launch(SplashScreen::class.java)
        onView(withId(R.id.splash_screen_id)).check(matches(isDisplayed()))
    }
}
