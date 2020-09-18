package com.example.flattingreview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_flat.*
import kotlinx.android.synthetic.main.activity_settings.*
import models.Flat
import models.Review

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
    private lateinit var review: Review
    private var address: String? = null
    private var overallRating: String? = null
    private var numberOfReviews: Int? = null
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

        // Bottom navigation
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.home_screen
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
                    val intent = Intent(this, CreateFlat::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

    }

    private fun set(){
        val addressText: TextView = findViewById(R.id.flat_address)
        val flatRating: TextView = findViewById(R.id.flat_rating)
//        val displayReview: CardView = findViewById(R.id.display_review)
        flatImage = findViewById(R.id.flat_image)
        flatRating.text = getString(R.string.reviews_for_flat_screen, overallRating, numberOfReviews)
        addressText.text = address!!.split(",")[0]
    }

    /**
     * Loads the data from the intent into the layout.
     */
    public override fun onStart() {
        super.onStart()
        flat = intent.getSerializableExtra("flat") as Flat
        address = flat.address
        overallRating = intent.getStringExtra("overallRating")
        numberOfReviews = intent.getIntExtra("numberOfRatings", 0)
        getReview(flat.flatID)
        set()
        val storage = FirebaseStorage.getInstance()
        val gsReference =
            storage.getReferenceFromUrl("gs://flattingreview.appspot.com/flats/image${flat.flatID}.jpg")
        GlideApp.with(this)
            .load(gsReference)
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(flat_image)


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
                            if (rev.comment != "") {
                                review = rev
                            }
                        }
                    }
                }
            }
        }
        reviewReference.orderByKey().addValueEventListener(reviewListener)
    }
}
