package com.example.flattingreview

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_in.signUpButton


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

        //click listener for the login button
        loginButton.setOnClickListener{
            doLogin()
        }

    }

    /**
     * Method to check email and password has been entered and login in an existing user.
     */
    private fun doLogin() {
       //if the email address is empty, set an error message
        if(enterEmailtv.text.toString().isEmpty()){
            enterEmailtv.error="Please enter email"
            enterEmailtv.requestFocus()
            return
        }
        //if the email is not a valid email, set an error message
        if(!Patterns.EMAIL_ADDRESS.matcher(enterEmailtv.text.toString()).matches()){
            enterEmailtv.error="Please enter a valid email"
            enterEmailtv.requestFocus()
            return
        }
        //if the password is empty, set an error message
        if(enterPasstv.text.toString().isEmpty()){
            enterPasstv.error="Please enter a password"
            enterPasstv.requestFocus()
            return
        }
        //if all of the checks are ok, then we need to login.
        auth.signInWithEmailAndPassword(enterEmailtv.text.toString(), enterPasstv.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Login failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
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
     * UpdateUI method to check if the user is null. If they are not null, then sign in is successful.
     *
     * @param currentUser a null firebase user that is not already signed in to the application.
     */
    private fun updateUI(currentUser: FirebaseUser?){
       if(currentUser!=null){
           //check if the user email is verified
           if(currentUser.isEmailVerified) {
               startActivity(Intent(this, HomeScreen::class.java))
               finish()
           } else{
               Toast.makeText(baseContext, "Please verify your email address.",Toast.LENGTH_SHORT).show()
           }
        }else{
           Toast.makeText(baseContext, "Login failed.",Toast.LENGTH_SHORT).show()
        }
    }
}
