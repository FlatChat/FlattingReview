package com.example.flattingreview

import  android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*
import java.io.IOException

/**
 * A class that displays a temporary splash screen when the user opens the app for the first time.
 * The name of the application will fade in using an application. The splash screen will show for 400milliseconds
 * and then the user will be directed to the sign in page.
 *
 * @author Nikki Meadows
 * @author Ryan Cole
 */
class SplashScreen : AppCompatActivity() {

    /**
     * This method contains the code and animation that will display the splash screen when the user
     * first installs the application.
     *
     * @param savedInstanceState the most recent state of the application.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        //animation for the flat chat
        val animation = AnimationUtils.loadAnimation(baseContext, R.anim.fadeanimation)
        splashFlatTV.startAnimation(animation)
        house.startAnimation(animation)
        //controls how long the splash screen shows for
        val background = object : Thread() {
            override fun run() {
                try {
                    sleep(4000)
                    val intent = Intent(baseContext, SignIn::class.java)
                    startActivity(intent)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }
}
