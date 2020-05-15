package com.example.flattingreview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignIn  : AppCompatActivity() {

    //global variable for firebase authentication
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        //connecting the sign in screen dummy button to the home screen
        tempSignInButton.setOnClickListener{
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }
        //connected the sign up button to the create an account screen
        signUpButton.setOnClickListener{
            val intent = Intent(this, create_account::class.java)
            startActivity(intent)
            finish()//kill the current activity
        }
    }
    /**
     * Check if the user is already signed in.
     */
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    /**
     * UpdateUI handler.
     *
     * @param currentUser a null firebase user that is not already signed in to the application.
     */
    fun updateUI(currentUser: FirebaseUser?){

    }
}
