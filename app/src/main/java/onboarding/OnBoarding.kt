package onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flatchat.app.HomeScreen
import com.flatchat.app.R
import com.flatchat.app.Search
import com.flatchat.app.Settings
import com.google.android.material.bottomnavigation.BottomNavigationView


class OnBoarding : AppCompatActivity() {

    private val firstScreen = Address()
    private val secondScreen = Details()
    private val thirdScreen = Image()
    public companion object Flat {
        var address = ""
        var bathrooms = ""
        var bedrooms = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        val position = 0

        setFragment(position)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.add_flat_screen
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.account_screen -> {
                    val intent = Intent(this, Settings::class.java)
                    startActivity(intent)
                    true
                }
                R.id.home_screen -> {
                    val intent = Intent(this, HomeScreen::class.java)
                    startActivity(intent)
                    true
                }
                R.id.search_screen -> {
                    val intent = Intent(this, Search::class.java)
                    startActivity(intent)
                    true
                }
                R.id.add_flat_screen -> {
                    true
                }
                else -> false
            }
        }
    }

    fun setFragment(position: Int){
        when (position) {
            0 -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view,
                firstScreen).commit()
            1 -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view,
                secondScreen).commit()
            2 -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view,
                thirdScreen).commit()
        }
//        if(position == 2){
//            nextButton.text = getString(R.string.finish)
//            nextButton.background = ContextCompat.getDrawable(this, drawable.button_fill)
//            nextButton.setTextColor(getColor(R.color.colorWhite))
//        } else {
//            nextButton.text = getString(R.string.next)
//            nextButton.background = ContextCompat.getDrawable(this, drawable.button_blue_outline)
//            nextButton.setTextColor(getColor(R.color.colorPrimary))
//        }
//
//        if(position == 0){
//            backButton.text = getString(R.string.cancel)
//            backButton.background = ContextCompat.getDrawable(this, drawable.button_fill)
//            backButton.setTextColor(getColor(R.color.colorWhite))
//        } else {
//            backButton.text = getString(R.string.back)
//            backButton.background = ContextCompat.getDrawable(this, drawable.button_blue_outline)
//            backButton.setTextColor(getColor(R.color.colorPrimary))
//        }
    }
}