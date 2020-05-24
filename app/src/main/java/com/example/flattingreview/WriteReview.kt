package com.example.flattingreview

import android.content.Intent
import android.icu.text.SimpleDateFormat
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import domain.Review
import java.util.*

/**
 * Class designed to collect the data inputted by the users from activity_write_review.xml
 * and write the data into the database.
 * @author Ryan
 */
class WriteReview : AppCompatActivity(), RatingBar.OnRatingBarChangeListener {

    private lateinit var submitButton: Button
    private lateinit var cleanliness: RatingBar
    private lateinit var landlord: RatingBar
    private lateinit var location: RatingBar
    private lateinit var value: RatingBar
    private lateinit var anon: Switch
    private var comment: String? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_review)

        collectInput()

        submitButton.setOnClickListener {
            saveObject()
        }
    }

    /**
     * Will get the users input from the activity_write_review screen and save
     * the data into variables
     */
    private fun collectInput(){
        submitButton = findViewById(R.id.submit_button)
        comment = findViewById<EditText>(R.id.comment_input).toString()
        cleanliness = findViewById(R.id.cleanliness)
        landlord = findViewById(R.id.landlord)
        location = findViewById(R.id.location)
        value = findViewById(R.id.value)
        anon = findViewById(R.id.anonSwitch)
    }

    /**
     * Gets the FlatID and UserID to attach to the review. Records a time stamp of when the
     * review was created. Get the firebase reference to save the data into. Creates a reviewID
     * through the firebase id system. Creates a reviews object. Writes the review object into
     * the database.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveObject(){
        // If the user is offline it still saves the data to upload later
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        // Set at 0 until we have the working flats
        val flatID = "0"
        // Current signed in user
        val userID = FirebaseAuth.getInstance().currentUser?.uid
        // When the review was created
        val date = Date()
        val newDate = Date(date.time + 604800000L * 2 + 24 * 60 * 60)
        val dt = SimpleDateFormat("yyyy-MM-dd")
        val stringDate: String = dt.format(newDate)
        // Database reference
        val reviewReference = FirebaseDatabase.getInstance().getReference("reviews")
        // Creates reviewID
        val reviewID = reviewReference.push().key
        // Create a review object
        val rev = Review(reviewID, userID, flatID, cleanliness.rating, landlord.rating,
            location.rating, value.rating, anon.isChecked, stringDate, comment)
        // Writes into database
        reviewReference.child(reviewID.toString()).setValue(rev)
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
