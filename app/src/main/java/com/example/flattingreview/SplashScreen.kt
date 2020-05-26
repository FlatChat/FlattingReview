package com.example.flattingreview

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*


class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        //animation for the flat chat

        val animation = AnimationUtils.loadAnimation(baseContext, R.anim.fadeanimation)
        splashFlatTV.startAnimation(animation)
        //controls how long the splash screen shows for
        val background = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(4000)
                    val intent = Intent(baseContext, SignIn::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        background.start()

    }
}
