package com.example.flattingreview

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import models.Flat
import kotlinx.android.synthetic.main.activity_flat.*

/**
 * This is the screen for a particular flat selected
 * by a user (when clicked on from the HomeScreen).
 * It is yet to be connected to the database, but
 * when fully functional will show reviews, images,
 * ratings and comments about a flat.
 * @author Meggie Morrison
 */
class FlatScreen : AppCompatActivity() {

    private lateinit var flatRef: DatabaseReference
    private var address: String? = null
    private var overallRating: String? = null
    private var numberOfReviews: Int? = null
    private lateinit var flat: Flat

    /**
     * This connects a reference to flats and reviews in the database.
     * It also has button listeners to take the user to other screens.
     *
     * @param  savedInstanceState the most recent state of the application.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flat)

        flatRef = FirebaseDatabase.getInstance().getReference("flats")

        show_reviews_button.setOnClickListener {
            val intent = Intent(this, ViewReviews::class.java)
            intent.putExtra("flat", flat)
            startActivity(intent)
        }

        add_review_button.setOnClickListener {
            val intent = Intent(this, WriteReview::class.java)
            intent.putExtra("flat", flat)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun set(){
        val addressText: TextView = findViewById(R.id.flat_address)
        val flatRating: TextView = findViewById(R.id.flat_rating)
        flatRating.text = "$overallRating ($numberOfReviews reviews)"
        addressText.text = address!!.split(",")[0]
    }

    /**
     * This method is not yet fully functional.
     * It will serve to load the data about the
     * selected flat from the database.
     */
    public override fun onStart() {
        super.onStart()
        flat = intent.getSerializableExtra("flat") as Flat
        address = flat.address
        overallRating = intent.getStringExtra("overallRating")
        numberOfReviews = intent.getIntExtra("numberOfRatings", 0)
        set()
    }
}
