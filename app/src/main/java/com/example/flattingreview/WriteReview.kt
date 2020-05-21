package com.example.flattingreview

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import domain.Review
import kotlinx.android.synthetic.main.layout_review.*
import java.time.LocalDateTime

// Write a message to the database
private lateinit var database: DatabaseReference


/**
 * Class designed to collect the data inputted by the users from activity_write_review.xml
 * and write the data into the database.
 * @author Ryan
 */
class WriteReview : AppCompatActivity(), RatingBar.OnRatingBarChangeListener {

    @RequiresApi(Build.VERSION_CODES.O) // To get the current date and time
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_review)

        database = Firebase.database.reference

        // Get inputs from the input fields
        val submitButton = findViewById<Button>(R.id.submit_button)
        val comment = findViewById<EditText>(R.id.comment)
        val cleanliness = findViewById<RatingBar>(R.id.cleanlinessRatingBar)
        val landlord = findViewById<RatingBar>(R.id.landlordRatingBar)
        val location = findViewById<RatingBar>(R.id.locationRatingBar)
        val value = findViewById<RatingBar>(R.id.valueRatingBar)
        val anon = findViewById<Switch>(R.id.anonSwitch)

        // Set at 0 until we have the working accounts
        val flatID = 3
        val userID = FirebaseAuth.getInstance().currentUser?.uid
        val reviewID = 3

        // When the review was created
        val current = LocalDateTime.now()

        // Button Listener will save the information into the database
        // when the submit button is clicked.
        submitButton.setOnClickListener {

            // Create a reference of a review
            val rev = Review(reviewID, userID, flatID, cleanliness.rating, landlord.rating, location.rating, value.rating, anon.isChecked, current)
            rev.comment = comment.text.toString() // Set separately because its optional

            Toast.makeText(this, "Rating is: " + cleanliness.rating, Toast.LENGTH_LONG).show()
            database.child("review").child(userID.toString()).setValue(user)
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

    override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {
        TODO("Not yet implemented")
    }
}
