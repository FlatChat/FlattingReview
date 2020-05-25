package com.example.flattingreview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*


class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        //connecting the splash screen dummy button to the sign in screen
       splashScreenButton.setOnClickListener{
            val intent = Intent(this,SignIn::class.java)
           startActivity(intent)
           finish()
       }

    }
}
