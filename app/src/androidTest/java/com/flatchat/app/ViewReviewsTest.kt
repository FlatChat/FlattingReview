package com.flatchat.app

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import models.Flat
import org.junit.Test

/**
 * Tests that everything is displayed properly in the view reviews activity.
 * @author Ryan
 */
class ViewReviewsTest {

    /**
     * Tests that everything is displayed.
     *
     */
    @Test
    fun onCreate() {
        val flat = Flat(
            "-M348TH",
            "48 Union St, North Dunedin",
            "5",
            "2")
        val intent = Intent(ApplicationProvider.getApplicationContext(), ViewReviews::class.java)
            .putExtra("flat", flat)
        val scenario = ActivityScenario.launch<ViewReviews>(intent)
        scenario.onActivity {}
    }
}