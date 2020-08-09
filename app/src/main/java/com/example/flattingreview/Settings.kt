package com.example.flattingreview

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
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
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }
    }

    private fun logout() {
        auth.signOut()
        finish()
        startActivity(Intent(this, SignIn::class.java)) //redirect user to sign in page
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
        if (id == R.id.home_screen) {
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }
        //If write a review option is pressed go to review screen
        if (id == R.id.write_review) {
            val intent = Intent(this, WriteReview::class.java)
            startActivity(intent)
        }
        //If contact us option is pressed go to contact us screen
        if (id == R.id.contact) {
            val intent = Intent(this, Settings::class.java)
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
