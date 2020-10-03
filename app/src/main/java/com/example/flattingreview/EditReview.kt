@file:Suppress("SpellCheckingInspection")

package com.example.flattingreview

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_write_review.*
import models.Flat
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
    private lateinit var flat: Flat
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

        flat = intent.getSerializableExtra("flat") as Flat

        // ***** mEggie added in! ******
        review = intent.getSerializableExtra("review") as Review
        setDisplay()
        setInput()


        // what is this doing?
        flat.flatID
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
            val intent = Intent(this, FlatScreen::class.java)
            intent.putExtra("flat", flat)
            startActivity(intent)
        }
        cancel.setOnClickListener {
            val intent = Intent(this, FlatScreen::class.java)
            intent.putExtra("flat", flat)
            startActivity(intent)
        }
    }

    private fun setDisplay(){
        val address = flat.address
        val addressText: TextView = findViewById(R.id.write_review_address)
        addressText.text = address!!.split(",")[0]
    }


    private fun setInput() {
        submitButton = findViewById(R.id.submit_button)
        // edit here??
        // make a new pop up ?? ... 6.18 in video
        //comment = findViewById<EditText>(R.id.comment1).text
        val comment = review.comment
        val commentBox: EditText = findViewById(R.id.comment1)
        commentBox.setText(comment)
        cleanliness = findViewById(R.id.cleanliness)
        landlord = findViewById(R.id.landlord)
        location = findViewById(R.id.location)
        value = findViewById(R.id.value)
        anon = findViewById(R.id.anonSwitch)
    }

    /**
     * Saves the object (review) to firebase.
     *
     */
    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveObject() {
        val flatID = flat.flatID
        // When the review was created
        val sdf = android.icu.text.SimpleDateFormat("dd MMMM yyyy")
        val currentDate = sdf.format(Date())
        // Creates reviewID
        val reviewID = reviewReference.push().key
        // Create a review object
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
        // Writes into database
        reviewReference.child(reviewID.toString()).setValue(rev)
    }
}