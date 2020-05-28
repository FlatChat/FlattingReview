package com.example.flattingreview

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Switch
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import domain.Review
import kotlinx.android.synthetic.main.activity_write_review.*
import java.util.Date

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
    private var comment: Editable? = null
    private lateinit var reviewReference: DatabaseReference
    private var reviewListener: ValueEventListener? = null
    private var name: String? = null
    private var userID = FirebaseAuth.getInstance().currentUser?.uid

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_review)
        collectInput()
        submitButton.setOnClickListener {
            saveObject()
        }
        cancel.setOnClickListener() {
            val intent = Intent(this, Flat::class.java)
            startActivity(intent)
        }

    }

    /**
     * Will get the users input from the activity_write_review screen and save
     * the data into variables
     */
    private fun collectInput() {
        submitButton = findViewById(R.id.submit_button)
        comment = findViewById<EditText>(R.id.comment1).text
        cleanliness = findViewById(R.id.cleanliness)
        landlord = findViewById(R.id.landlord)
        location = findViewById(R.id.location)
        value = findViewById(R.id.value)
        anon = findViewById(R.id.anonSwitch)
    }

    /**
     * Connects to the database and collects the users first name identified by their userID
     */
    public override fun onStart() {
        super.onStart()
        reviewReference = FirebaseDatabase.getInstance().getReference("users")
        val reviewListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.w("WriteReview", "loadItem:onCancelled")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val u = ds.child("userID").value as String
                    if (u == userID) {
                        name = ds.child("firstNameUsers").value as String
                        Log.d("WriteReview", "" + ds.child("firstNameUsers").value as String)
                    }
                }
            }
        }
        reviewReference.orderByKey().addValueEventListener(reviewListener)
    }

    /**
     * Gets the FlatID and UserID to attach to the review. Records a time stamp of when the
     * review was created. Get the firebase reference to save the data into. Creates a reviewID
     * through the firebase id system. Creates a reviews object. Writes the review object into
     * the database.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveObject() {
        // Set at 0 until we have the working flats
        val flatID = "0"
        // Current signed in user

        // When the review was created
        val sdf = android.icu.text.SimpleDateFormat("dd MMMM yyyy")
        val currentDate = sdf.format(Date())
        // Database reference
        val reviewReference = FirebaseDatabase.getInstance().getReference("reviews")
        // Creates reviewID
        val reviewID = reviewReference.push().key
        // Create a review object

        Log.d("WriteReview", "" + cleanliness.rating.toDouble())

        val rev = Review(
            reviewID,
            userID,
            flatID,
            name.toString(),
            cleanliness.rating + 0.1,
            landlord.rating + 0.1,
            location.rating + 0.1,
            value.rating + 0.1,
            anon.isChecked,
            currentDate,
            comment.toString()
        )
        Log.d("WriteReview", "rev")
        // Writes into database
        reviewReference.child(reviewID.toString()).setValue(rev)
        val intent = Intent(this, Flat::class.java)
        startActivity(intent)
    }

    //below code is all for the action bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

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
            val intent = Intent(this, Contact::class.java)
            startActivity(intent)
        }
        //If logout option is selected then redirect user to the login screen
        if (id == R.id.settings) {
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {
        TODO("Not yet implemented")
    }
}

