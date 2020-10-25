package onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.flattingreview.HomeScreen
import com.example.flattingreview.R
import com.example.flattingreview.R.drawable
import com.example.flattingreview.Search
import com.example.flattingreview.Settings
import com.google.android.material.bottomnavigation.BottomNavigationView


class OnBoarding : AppCompatActivity() {

    private val firstScreen = Address()
    private val secondScreen = SecondScreen()
    private val thirdScreen = ThirdScreen()
    private lateinit var nextButton: Button
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        var position = 0
        nextButton = findViewById<Button>(R.id.button_next)
        backButton = findViewById<Button>(R.id.button_back)

        setFragment(position)

        nextButton.setOnClickListener {
            position += 1
            if(position == 3){
                val intent = Intent(this, HomeScreen::class.java)
                startActivity(intent)
            } else {
                setFragment(position)
            }
        }

        backButton.setOnClickListener {
            position -= 1
            if(position == -1){
                val intent = Intent(this, HomeScreen::class.java)
                startActivity(intent)
            } else {
                setFragment(position)
            }
        }

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

    private fun setFragment(position: Int){
        when (position) {
            0 -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view,
                firstScreen).commit()
            1 -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view,
                secondScreen).commit()
            2 -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view,
                thirdScreen).commit()
        }
        if(position == 2){
            nextButton.text = getString(R.string.finish)
            nextButton.background = ContextCompat.getDrawable(this, drawable.button_fill)
            nextButton.setTextColor(getColor(R.color.colorWhite))
        } else {
            nextButton.text = getString(R.string.next)
            nextButton.background = ContextCompat.getDrawable(this, drawable.button_blue_outline)
            nextButton.setTextColor(getColor(R.color.colorPrimary))
        }

        if(position == 0){
            backButton.text = getString(R.string.cancel)
            backButton.background = ContextCompat.getDrawable(this, drawable.button_fill)
            backButton.setTextColor(getColor(R.color.colorWhite))
        } else {
            backButton.text = getString(R.string.back)
            backButton.background = ContextCompat.getDrawable(this, drawable.button_blue_outline)
            backButton.setTextColor(getColor(R.color.colorPrimary))
        }
    }
}