package com.example.flattingreview

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_account.*
import models.Users

/**
 * A class that allows the user to create a new account using their email address and a password.
 * Once the user has successfully created a new account they will be redirected to the sign in page where
 * they can sign in with their new credentials.
 * @author Nikki Meadows
 */
class CreateAccount : AppCompatActivity() {

    //global variable for firebase authentication
    private lateinit var auth: FirebaseAuth
    private lateinit var firstNameUser: Editable
    private lateinit var lastNameUser: Editable
    private lateinit var emailUser: Editable


    /**
     * A method that gets the current firebase user authentication instance and calls the sign up user method
     * when the sign up button has been pressed by the user. This method also calls the collect input method
     * to collect the user information that the user has entered into the text fields.
     *
     * @param savedInstanceState the most recent state of the application.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        collectInput()

        //set a listener for the sign up button to check if....
        signUpButton.setOnClickListener {
            signUpUser()
        }
    }

    /**
     * A method that collects the user information that has been entered into the text fields containing the users
     * first name, last name and email address. The information is saved into new variables.
     *
     */
    private fun collectInput() {
        firstNameUser = findViewById<EditText>(R.id.firstNameTV).text
        lastNameUser = findViewById<EditText>(R.id.lastNameTV).text
        emailUser = findViewById<EditText>(R.id.email).text
    }

    /**
     * Method to check that the user has entered in an email and password before they are able to sign up.
     * If any checks do not pass, then the method fails. This method also saves a user object (user ID, first name, last name and
     * email) to the database.
     */
    private fun signUpUser() {
        //if the first name is empty, set an error message
        if (firstNameTV.text.toString().isEmpty()) {
            firstNameTV.error = "Please enter your first name"
            firstNameTV.requestFocus()
            return
        }
        //if the last name is empty, set an error message
        if (lastNameTV.text.toString().isEmpty()) {
            lastNameTV.error = "Please enter your last name"
            lastNameTV.requestFocus()
            return
        }
        //if the email address is empty, set an error message
        if (email.text.toString().isEmpty()) {
            email.error = "Please enter email"
            email.requestFocus()
            return
        }
        //if the email is not a valid email, set an error message
        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
            email.error = "Please enter a valid email"
            email.requestFocus()
            return
        }
        //if the password is empty, set an error message
        if (enterPass1.text.toString().isEmpty()) {
            enterPass1.error = "Please enter a password"
            enterPass1.requestFocus()
            return
        }
        //if all the tests pass, then a user account can be created
        auth.createUserWithEmailAndPassword(email.text.toString(), enterPass1.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user!!.sendEmailVerification()
                        .addOnCompleteListener {
                            if (task.isSuccessful) {
                                val userID = user.uid
                                val userReference =
                                    FirebaseDatabase.getInstance().getReference("users")
                                val usersDatabase = Users(
                                    userID,
                                    firstNameUser.toString(),
                                    lastNameUser.toString(),
                                    emailUser.toString()
                                )
                                //write to the database
                                userReference.child(userID).setValue(usersDatabase)
                                startActivity(Intent(this, SignIn::class.java))
                                finish()
                            }
                        }
                } else {
                    // If sign in fails, display a message to the user. Failure could be because of a network issue.
                    Toast.makeText(
                        baseContext, "Sign Up failed. Please try again soon.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
