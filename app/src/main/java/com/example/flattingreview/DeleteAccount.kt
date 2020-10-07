package com.example.flattingreview

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_delete_account.*

/**
 * A class containing a the required methods to allow a user to permanently delete their account.
 *
 * @author Nikki Meadows
 */
class DeleteAccount : AppCompatActivity() {
//Testing something again
    private lateinit var auth: FirebaseAuth

    /**
     * A method that gets the current firebase user authentication instance and calls the delete account method
     * when the delete account button has been pressed by the user.
     *
     * @param savedInstanceState the most recent state of the application.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_account)
        auth = FirebaseAuth.getInstance()
        //delete user account on the delete account screen calls a method called delete account
        confirmDelete.setOnClickListener {
            deleteAccount()
        }
    }

    private fun deleteAccount() {
        //if all of the text fields are not empty
        if (emailET.text.isNotEmpty() &&
            passwordET.text.isNotEmpty()
        ) {
            val user = auth.currentUser
            //if user is already logged in, then we can re-authenticate the user user
            if (user != null && user.email != null) {
                // Get auth credentials from the user for re-authentication.
                val credential = EmailAuthProvider
                    .getCredential(user.email!!, passwordET.text.toString())

                // Prompt the user to re-provide their sign-in credentials
                user.reauthenticate(credential)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Re-authentication is successful",
                                Toast.LENGTH_SHORT
                            )
                            user.delete()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(
                                            this,
                                            "Account deleted",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        auth.signOut()//sign out the user and make them login again
                                        startActivity(Intent(this, LandingPage::class.java))
                                        finish()//close the current activity
                                    }
                                }
                        } else {
                            Toast.makeText(this, "Account was not deleted", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }
        }
    }
}