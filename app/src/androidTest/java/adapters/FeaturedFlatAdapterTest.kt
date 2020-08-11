package adapters

import android.app.Application
import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.flattingreview.HomeScreen
import com.example.flattingreview.R
import com.example.flattingreview.SplashScreen
import models.Flat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import kotlin.coroutines.coroutineContext


class FeaturedFlatAdapterTest {

    private var list: ArrayList<Flat> = ArrayList()

    @Before
    fun setUp() {
        val flat = Flat()
        flat.flatID = "-SH893HTB9VW90EI"
        list.add(flat)
    }

    @Test
    fun onCreateViewHolder() {
        val activityScenario= ActivityScenario.launch(HomeScreen::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.home_screen))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun onBindViewHolder() {
        val activityScenario= ActivityScenario.launch(HomeScreen::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.home_screen))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun getItemCount() {
            val initialExpected = 1
            val initialActual = list.size
            assertEquals(initialExpected, initialActual)
    }
}