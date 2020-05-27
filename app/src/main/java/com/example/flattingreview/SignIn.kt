package com.example.flattingreview

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_in.signUpButton

/**
 * A class that allows the user to sign in to their account, or alternatively access the create account page
 * if the user does not have an existing account. If the user has forgotten their password, they will be able to
 * reset their password from this activity.
 * @author Nikki Meadows
 */
class SignIn  : AppCompatActivity() {

    //global variable for firebase authentication
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()


        //connected the sign up button to the create an account screen
        signUpButton.setOnClickListener{
            val intent = Intent(this, CreateAccount::class.java)
            startActivity(intent)
            finish()//kill the current activity
        }

        //click listener for the login button
        loginButton.setOnClickListener{
            doLogin()
        }
        //click listener for the forgot password button
        forgotPassButton.setOnClickListener {
            val builder=AlertDialog.Builder(this)
            builder.setTitle("Forgot Password")
            val view=layoutInflater.inflate(R.layout.dialog_forgot_password,null)
            val username=view.findViewById<EditText>(R.id.et_username)
            builder.setView(view)
            builder.setPositiveButton("Reset",DialogInterface.OnClickListener { _,  _->
                forgotPassword(username)
            })
            builder.setNegativeButton("Close",DialogInterface.OnClickListener { _, _->
                builder.show()
            })
        }

    }

    /**
     * A method that allows the user to reset their password.
     * @param username from the dialog for validation.
     */
    private fun forgotPassword(username:EditText){

        if(username.text.toString().isEmpty()){
            return
        }
        //if the email is not a valid email, set an error message
        if(!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()){
            return
        }
        auth.sendPasswordResetEmail(username.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email Sent.",Toast.LENGTH_SHORT).show()
                }
            }
    }

    /**
     * A method to check email and password has been entered and login in an existing user.
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
     * A method to check if the user is already signed in.
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
