package com.flatchat.app

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.junit.Test

class SearchTest {

    @Test
    fun onCreate() {
        ActivityScenario.launch(Search::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.search_bar_text))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

//    @Test
//    fun onItemClick() {
//        val instance = Search()
//        val flat = Flat(
//            "-M348TH",
//            "48 Union St, North Dunedin",
//            "5",
//            "2")
//        instance.onItemClick(flat)
//    }
}