package com.flatchat.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_settings.*

/**
 * A class designed to allow the user to logout and be redirected to the sign in screen. This class also
 * allows the user to access another screen where they are able to change their own password..
 * @author Nikki Meadows
 */
class Settings : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var name: String? = null
    private var userID = FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var userReference: DatabaseReference

    /**
     * A method that gets the current firebase user authentication instance. This method also contains a click listener
     * that will call the logout method when the user clicks the logout button. Additionally, this method contains a click listener
     * that will redirect the user to the change password screen when the user clicks a change password button.
     *
     * @param savedInstanceState the most recent state of the application.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_settings)
        super.onCreate(savedInstanceState)

        userReference = FirebaseDatabase.getInstance().getReference("users")

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.account_screen

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.account_screen -> {
                    true
                }
                R.id.home_screen -> {
                    val intent = Intent(this, HomeScreen::class.java)
                    startActivity(intent)
                    true
                }
                R.id.search_screen -> {
                    val intent = Intent(this, Search::class.java)
                    startActivity(intent)
                    true
                }
                R.id.add_flat_screen -> {
                    val intent = Intent(this, CreateFlat::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        val userListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.w("WriteReview", "loadItem:onCancelled")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val u = ds.child("userID").value as String
                    if (u == userID) {
                        name = ds.child("firstNameUsers").value as String
                    }
                }
                person_letter.text = name?.substring(0, 1)
            }
        }
        userReference.orderByKey().addValueEventListener(userListener)

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
    }

    private fun logout() {
        auth.signOut()
        finish()
        val intent = Intent(this, LandingPage::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}
