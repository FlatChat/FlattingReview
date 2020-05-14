package com.example.flattingreview

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Switch
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import domain.Review
import java.time.LocalDateTime

// Write a message to the database
private val database = Firebase.database
private val myRef = database.getReference("message")

class WriteReview : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_review)

        // Get inputs
        val buttonClick = findViewById<Button>(R.id.submit_button)
        val comment = findViewById<EditText>(R.id.comment).text.toString()
        val cleanliness = findViewById<RatingBar>(R.id.cleanlinessRatingBar).rating
        val landlord = findViewById<RatingBar>(R.id.landlordRatingBar).rating
        val location = findViewById<RatingBar>(R.id.locationRatingBar).rating
        val value = findViewById<RatingBar>(R.id.valueRatingBar).rating
        val anon = findViewById<Switch>(R.id.anonSwitch).isChecked

        // Set at 0 until we have the working accounts
        val flatID = 0
        val userID = 0
        val reviewID = 0

        // When the review was created
        val current = LocalDateTime.now()

        // Create a reference of a review
        val rev = Review(reviewID, userID, flatID, cleanliness, landlord, location, value, anon, current)
        rev.comment = comment // Set separately because its optional

        // Button Listener will save the information into the database
        // when the submit button is clicked.
        buttonClick.setOnClickListener {
            myRef.setValue(rev)
        }
    }

    //below code is all for the action bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId

        //If home screen option is pressed go to home screen
        if(id==R.id.homescreen)
        {
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }
        //If write a review option is pressed go to review screen
        if(id==R.id.writereview)
        {
            val intent = Intent(this, WriteReview::class.java)
            startActivity(intent)
        }
        //If contact us option is pressed go to contact us screen
        if(id==R.id.contact)
        {
            val intent = Intent(this, Contact::class.java)
            startActivity(intent)
        }
        //If logout option is selected then redirect user to the login screen
        if(id==R.id.logout)
        {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
