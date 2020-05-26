package com.example.flattingreview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePassword : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        auth= FirebaseAuth.getInstance()
        //change password button on the change password screen calls a method called change password
        changePasBut.setOnClickListener {
            changePassword()
        }
    }

    /**
     * Method to change the users current password to a new password.
     */
    private fun changePassword(){
        //if all of the text fields are not empty, then we need to change the password
        if(currentPassET.text.isNotEmpty() &&
                newPassET.text.isNotEmpty() &&
                confirmPassET.text.isNotEmpty()){
            //check if new password and confirm password are the same
            if(newPassET.text.toString().equals(confirmPassET.text.toString())){
                val user=auth.currentUser
                //if user is already logged in, then we can re-authenticate the user user
                if(user!=null && user.email!=null){
                    // Get auth credentials from the user for re-authentication.
                    val credential = EmailAuthProvider
                        .getCredential(user.email!!, currentPassET.text.toString())

                    // Prompt the user to re-provide their sign-in credentials
                    user.reauthenticate(credential)
                        .addOnCompleteListener {
                            if(it.isSuccessful){
                                Toast.makeText(this, "Re-authentication is successful", Toast.LENGTH_SHORT).show()
                                user!!.updatePassword(newPassET.text.toString())//pass the new password
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(this, "Password changed", Toast.LENGTH_SHORT).show()
                                            auth.signOut()//sign out the user and make them login again
                                            startActivity(Intent(this, SignIn::class.java))
                                            finish()//close the current activity
                                        }
                                    }
                            } else{
                                Toast.makeText(this, "Re-authentication failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                }else{
                    startActivity(Intent(this,SignIn::class.java))
                    finish()
                }
            } else{
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
        }
    }
}