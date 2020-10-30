@file:Suppress("SpellCheckingInspection")

package com.flatchat.app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_write_review.*
import models.Review
import java.util.*

/**
 * Class to edit written reviews and save the updated version
 * to the database.
 * @author Ryan
 * @author Meggie
 */

class EditReview : AppCompatActivity () {

    private lateinit var submitButton: Button
    private lateinit var cleanliness: RatingBar
    private lateinit var landlord: RatingBar
    private lateinit var location: RatingBar
    private lateinit var value: RatingBar
    private lateinit var anon: SwitchCompat
    private var comment: Editable? = null
    private lateinit var userReference: DatabaseReference
    private lateinit var reviewReference: DatabaseReference
    private var name: String? = null
    private var userID = FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var review: Review

    /**
     * The onCreate method calls the setInputs() method which sets all the
     * input elements to a variable. This method also has the cancel button which takes
     * you back to the Flat.kt screen and the submit button that will call the
     * saveObject() method.
     *
     * @param savedInstanceState
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_review)

        userReference = FirebaseDatabase.getInstance().getReference("users")
        reviewReference = FirebaseDatabase.getInstance().getReference("reviews")

        review = (intent.getSerializableExtra("review") as? Review)!!
        setInput()

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
            }
        }
        userReference.orderByKey().addValueEventListener(userListener)

        submitButton.setOnClickListener {
            saveObject()
            val intent = Intent(this, HomeScreen::class.java)
            intent.putExtra("review", review)
            startActivity(intent)
        }
        cancel.setOnClickListener {
            val intent = Intent(this, HomeScreen::class.java)
            intent.putExtra("review", review)
            startActivity(intent)
        }
    }

    /**
     * Function loads input from old review,
     * then sets each variable for a Review
     * to the new input entered.
     *
     */
    private fun setInput() {
        submitButton = findViewById(R.id.submit_button)
        val commentBox: EditText = findViewById(R.id.comment1)
        commentBox.setText(review.comment)
        comment = commentBox.text
        val cleanBox: RatingBar = findViewById(R.id.cleanliness)
        cleanBox.rating = review.cleanliness.toFloat()
        cleanliness = cleanBox
        val landBox: RatingBar = findViewById(R.id.landlord)
        landBox.rating = review.landlord.toFloat()
        landlord = landBox
        val locationBox: RatingBar = findViewById(R.id.location)
        locationBox.rating = review.location.toFloat()
        location = locationBox
        val valueBox: RatingBar = findViewById(R.id.value)
        valueBox.rating = review.value.toFloat()
        value = valueBox
        val anonBox: SwitchCompat = findViewById(R.id.anonSwitch)
        anonBox.isChecked = review.anonymous
        anon = anonBox
    }

    /**
     * Saves the updated object (review) to firebase.
     *
     */
    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveObject() {
        val flatID = review.flatID
        // When the review was created
        val sdf = android.icu.text.SimpleDateFormat("dd MMMM yyyy")
        val currentDate = sdf.format(Date())
        // Creates reviewID
        val reviewID = review.reviewID
        // Create a review object
        val rev = Review(
            reviewID,
            userID,
            flatID,
            review.name.toString(),
            cleanliness.rating + 0.1,
            landlord.rating + 0.1,
            location.rating + 0.1,
            value.rating + 0.1,
            anon.isChecked,
            currentDate,
            comment.toString()
        )
        // Writes into database
        reviewReference.child(reviewID.toString()).setValue(rev)
    }
}