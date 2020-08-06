package com.example.flattingreview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.android.synthetic.main.activity_settings.*

/**
 * A class designed to allow the user to logout and be redirected to the sign in screen. This class also
 * allows the user to access another screen where they are able to change their password.
 * @author Nikki Meadows
 */
class Settings : AppCompatActivity() {
    //global variable for firebase authentication
    private lateinit var auth: FirebaseAuth

    /**
     * A method that gets the current firebase user authentication instance. This method also contains a click listener
     * that will call the logout method when the user clicks the logout button. Additionally, this method contains a click listener
     * that will redirect the user to the change password screen when the user clicks the change password button.
     *
     * @param savedInstanceState the most recent state of the application.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_settings)
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth

        //connecting the change password button to the change password screen
        changePassBut.setOnClickListener {
            val intent = Intent(this, ChangePassword::class.java)
            startActivity(intent)
        }
        //logging out the user
        logoutBut.setOnClickListener {
            logout()
        }
        //connecting the delete account button to the delete account screen
        deleteAccountButton.setOnClickListener{
            val intent = Intent(this, DeleteAccount::class.java)
            startActivity(intent)
        }
        //connecting the need help button to the account screen
        buttonHelp.setOnClickListener{
            val intent = Intent(this, Account::class.java)
            startActivity(intent)
        }
    }

    /**
     * A method that will logout the current user and re-direct the user to the login screen.
     */
    private fun logout() {
        auth.signOut()
        finish()
        startActivity(Intent(this, SignIn::class.java)) //redirect user to sign in page
    }
}
