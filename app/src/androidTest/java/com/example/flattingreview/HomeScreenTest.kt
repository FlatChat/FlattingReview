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
class HomeScreenTest{

    @Test
    fun test_isActivitiyInView() {
        val activityScenario=ActivityScenario.launch(HomeScreen::class.java)
        onView(withId(R.id.home_screen_tag)).check(matches(isDisplayed()))

    }
    //a test to check the visibility of show all popular flats button
    @Test
    fun test_Visibility_CreateFlat_Button() {
        val activityScenario=ActivityScenario.launch(HomeScreen::class.java)
        onView(withId(R.id.show_all_popular_flats))
            .check(matches(isDisplayed()))
    }
}