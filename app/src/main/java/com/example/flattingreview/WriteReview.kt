package com.example.flattingreview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// Write a message to the database
private val database = Firebase.database
private val myRef = database.getReference("message")

class WriteReview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_review)

        // Get reference to button
        val buttonClick = findViewById<Button>(R.id.submit_button)
        // Get reference to the text
        val comment = findViewById<EditText>(R.id.comment)
        // Get reference to the rating bars
        val cleanliness = findViewById<RatingBar>(R.id.cleanlinessRatingBar)
        val landlord = findViewById<RatingBar>(R.id.landlordRatingBar)
        val location = findViewById<RatingBar>(R.id.locationRatingBar)
        val value = findViewById<RatingBar>(R.id.valueRatingBar)

        // Button Listener will save the information into the database
        // when the submit button is clicked.
        buttonClick.setOnClickListener {
            myRef.setValue(comment)
            myRef.setValue(cleanliness)
            myRef.setValue(landlord)
            myRef.setValue(location)
            myRef.setValue(value)
            //myRef.setValue(userID)
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
