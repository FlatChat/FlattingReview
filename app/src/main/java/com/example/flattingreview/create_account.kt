package com.example.flattingreview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_create_account.*

class create_account : AppCompatActivity() {

    //global variable for firebase authentication
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        //set a listener for the sign up button to check if....
        signUpButton.setOnClickListener {
            signUpUser()
        }
    }

    /**
     * Method to check that the user has entered in an email and password before they are able to sign up.
     * If any checks do not pass, then the method fails.
     */
    private fun signUpUser(){
            //if the email address is empty, set an error message
            if(email.text.toString().isEmpty()){
                email.error="Please enter email"
                email.requestFocus()
                return
            }
            //if the email is not a valid email, set an error message
            if(!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
                email.error="Please enter a valid email"
                email.requestFocus()
                return
            }
            //if the password is empty, set an error message
            if(enterPass1.text.toString().isEmpty()){
                enterPass1.error="Please enter a password"
                enterPass1.requestFocus()
                return
            }
        //if all the tests pass, then a user account can be created
        auth.createUserWithEmailAndPassword(email.text.toString(), enterPass1.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user =auth.currentUser
                    user!!.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this,SignIn::class.java))
                                finish()
                            }
                        }
                } else {
                    // If sign in fails, display a message to the user. Failure could be because of a network issue.
                    Toast.makeText(baseContext, "Sign Up failed. Please try again soon.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
}
