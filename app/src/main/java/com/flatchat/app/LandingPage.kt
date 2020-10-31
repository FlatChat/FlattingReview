package com.flatchat.app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_landing_page.*

class LandingPage : AppCompatActivity() {

    //global variable for firebase authentication
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)

        auth = FirebaseAuth.getInstance()

        create_account_button.setOnClickListener {
            val intent = Intent(this, CreateAccount::class.java)
            startActivity(intent)
        }

        login_button.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }
    }

    /**
     * A method to check if the user is already signed in.
     */
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?){
        if(currentUser!=null){
            //check if the user email is verified
            if(currentUser.isEmailVerified) {
                startActivity(Intent(this, HomeScreen::class.java))
                finish()
            } else{
                Toast.makeText(baseContext, "Please verify your email address.", Toast.LENGTH_SHORT).show()
            }
        }else{
           Toast.makeText(baseContext, "Login failed.",Toast.LENGTH_SHORT).show()
        }
    }
}