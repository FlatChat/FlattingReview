package com.example.flattingreview

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import domain.Review
import java.time.LocalDateTime

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

        // Get inputs from the input fields
        val submitButton = findViewById<Button>(R.id.submit_button)
        val comment = findViewById<EditText>(R.id.comment)
        val cleanliness = findViewById<RatingBar>(R.id.cleanliness)
        val landlord = findViewById<RatingBar>(R.id.landlord)
        val location = findViewById<RatingBar>(R.id.location)
        val value = findViewById<RatingBar>(R.id.value)
        val anon = findViewById<Switch>(R.id.anonSwitch)

        // Set at 0 until we have the working flats
        val flatID = "0"
        // Current signed in user
        val userID = FirebaseAuth.getInstance().currentUser?.uid

        // When the review was created
        val current = LocalDateTime.now()

        // Button Listener will save the information into the database
        // when the submit button is clicked.
        submitButton.setOnClickListener {

            val myRef = FirebaseDatabase.getInstance().getReference("reviews")
            // Creates reviewID
            val reviewID = myRef.push().key

            // Create a review object
            val rev = Review(reviewID, userID, flatID, cleanliness.rating, landlord.rating,
                location.rating, value.rating, anon.isChecked, current, comment.text.toString())

            myRef.child(reviewID.toString()).setValue(rev)
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
