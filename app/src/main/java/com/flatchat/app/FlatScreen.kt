package com.flatchat.app

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_flat.*
import models.Flat
import models.Review
import onboarding.OnBoarding

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
    private lateinit var reviewReference: DatabaseReference
    private var reviewList: ArrayList<Review> = ArrayList()
    private var address: String? = null
    private var overallRating: String? = null
    private lateinit var flat: Flat
    private lateinit var flatImage: ImageView

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
        reviewReference = FirebaseDatabase.getInstance().getReference("reviews")

        flat = intent.getSerializableExtra("flat") as Flat
        getReview(flat.flatID)
        address = flat.address

        val storage = FirebaseStorage.getInstance()
        val gsReference =
            storage.getReferenceFromUrl("gs://flattingreview.appspot.com/flats/image${flat.flatID}.jpg")
        GlideApp.with(this)
            .load(gsReference)
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(flat_image)

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

        flatRef.child(flat.flatID.toString()).child("views").setValue(flat.views + 1)

        // Bottom navigation
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.account_screen -> {
                    val intent = Intent(this, Settings::class.java)
                    startActivity(intent)
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
                    val intent = Intent(this, OnBoarding::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        reviewList.clear()
        getReview(flat.flatID)
    }

    private fun set(){
        val addressText: TextView = findViewById(R.id.flat_address)
        val flatRating: TextView = findViewById(R.id.flat_rating)

        val cleanlinessBar: ProgressBar = findViewById(R.id.cleanliness_bar)
        val landlordBar: ProgressBar = findViewById(R.id.landlord_bar)
        val locationBar: ProgressBar = findViewById(R.id.location_bar)
        val valueBar: ProgressBar = findViewById(R.id.value_bar)
        cleanlinessBar.progressTintList = ColorStateList.valueOf(Color.BLACK)
        landlordBar.progressTintList = ColorStateList.valueOf(Color.BLACK)
        locationBar.progressTintList = ColorStateList.valueOf(Color.BLACK)
        valueBar.progressTintList = ColorStateList.valueOf(Color.BLACK)
        val list = calculateRating()
        cleanlinessBar.progress = (list[0] * 10).toInt()
        landlordBar.progress = (list[1] * 10).toInt()
        locationBar.progress = (list[2] * 10).toInt()
        valueBar.progress = (list[3] * 10).toInt()
        flatImage = findViewById(R.id.flat_image)
        overallRating = "%.1f".format((list[0] + list[1] + list[2] + list[3]) / 4)
        flatRating.text = getString(R.string.reviews_for_flat_screen, overallRating, reviewList.size)
        addressText.text = address!!.split(",")[0]
    }

    private fun calculateRating(): MutableList<Double> {
        var count = 0
        val list = mutableListOf(0.0, 0.0, 0.0, 0.0)
        for(rev in reviewList){
            list[0] += (rev.cleanliness - 0.1)
            list[1] += (rev.landlord  - 0.1)
            list[2] += (rev.location - 0.1)
            list[3] += (rev.value - 0.1)
            count++
        }
        for(i in 0 until 4){
            list[i] = list[i] / count
        }
        return list
    }

    private fun getReview(id: String?) {
        val reviewListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Log.w("ViewReview", "loadItem:onCancelled")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    if (id == ds.child("flatID").value as String) {
                        val rev = ds.getValue(Review::class.java)
                        if (rev != null) {
                            reviewList.add(rev)
                        }
                    }
                }
                set()
            }
        }
        reviewReference.orderByKey().addValueEventListener(reviewListener)
    }
}
