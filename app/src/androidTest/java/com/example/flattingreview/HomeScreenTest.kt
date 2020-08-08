package com.example.flattingreview/*
package com.example.flattingreview


import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith

*/
/**
 * A class that tests the UI of the home screen.
 *
 * @author Nikki Meadows
 *//*

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeScreenTest{

    @Test
    fun test_isActivityInView() {
        val activityScenario=ActivityScenario.launch(HomeScreen::class.java)
        onView(withId(R.id.homeScreenTag)).check(matches(isDisplayed()))

    }
    //a test to check the visibility of the create a flat button
    @Test
    fun test_Visibility_CreateFlat_Button() {
        val activityScenario=ActivityScenario.launch(HomeScreen::class.java)
        onView(withId(R.id.createFlatButton))
            .check(matches(isDisplayed()))
    }
}*/
