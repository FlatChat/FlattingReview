package com.example.flattingreview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignIn  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        //connecting the sign in screen dummy button to the home screen
        tempSignInButton.setOnClickListener{
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }
        //connected the sign up button to the create an account screen
        signUpButton.setOnClickListener{
            val intent = Intent(this, create_account::class.java)
            startActivity(intent)
        }
    }
}
