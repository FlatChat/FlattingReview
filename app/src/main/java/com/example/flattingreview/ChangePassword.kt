package com.example.flattingreview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_change_password.*

/**
 * A class to allow the user to change their password. Once the password has been changed successfully, the user
 * will be redirected to the sign in screen where they will then need to sign in with their new password.
 *
 * @author Nikki Meadows
 */
class ChangePassword : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    /**
     * A method that gets the current firebase user authentication instance and calls the change password method
     * when the change password button has been pressed by the user.
     *
     * @param savedInstanceState the most recent state of the application.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        auth = FirebaseAuth.getInstance()
        //change password button on the change password screen calls a method called change password
        changePasBut.setOnClickListener {
            changePassword()
        }
    }

    /**
     * A method to change the users current password to a new password. The method checks that the user has
     * filled in the required text fields. If all of the fields have been filled in, the method checks that the
     * new password and confirm password match. If all checks pass, the user redirected to the sign in screen.
     * If not, a toast pop up will inform the user of what they have done wrong.
     */
    private fun changePassword() {
        //if all of the text fields are not empty, then we need to change the password
        if (currentPassET.text.isNotEmpty() &&
            newPassET.text.isNotEmpty() &&
            confirmPassET.text.isNotEmpty()
        ) {
            //check if new password and confirm password are the same
            if (newPassET.text.toString().equals(confirmPassET.text.toString())) {
                val user = auth.currentUser
                //if user is already logged in, then we can re-authenticate the user user
                if (user != null && user.email != null) {
                    // Get auth credentials from the user for re-authentication.
                    val credential = EmailAuthProvider
                        .getCredential(user.email!!, currentPassET.text.toString())

                    // Prompt the user to re-provide their sign-in credentials
                    user.reauthenticate(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(
                                    this,
                                    "Re-authentication is successful",
                                    Toast.LENGTH_SHORT
                                ).show()
                                user.updatePassword(newPassET.text.toString())//pass the new password
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(
                                                this,
                                                "Password changed",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            auth.signOut()//sign out the user and make them login again
                                            startActivity(Intent(this, SignIn::class.java))
                                            finish()//close the current activity
                                        }
                                    }
                            } else {
                                Toast.makeText(this, "Re-authentication failed", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                } else {
                    startActivity(Intent(this, SignIn::class.java))
                    finish()
                }
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
        }
    }

    //below code is all for the action bar
    /**
     * The following code is for the action bar.
     * Different options are displayed to take the
     * user to different screens.
     *
     * @param menu the menu file containing the action bar options.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * A method that allows the user to select a specific screen from the action bar.
     *
     * @param item the different action bar options for the user to select.
     * @return the option that the user has selected
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        //If home screen option is pressed go to home screen
        if (id == R.id.homescreen) {
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }
        //If write a review option is pressed go to review screen
        if (id == R.id.writereview) {
            val intent = Intent(this, WriteReview::class.java)
            startActivity(intent)
        }
        //If contact us option is pressed go to contact us screen
        if (id == R.id.contact) {
            val intent = Intent(this, Account::class.java)
            startActivity(intent)
        }
        //If logout option is selected then redirect user to the login screen
        if (id == R.id.settings) {
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}

