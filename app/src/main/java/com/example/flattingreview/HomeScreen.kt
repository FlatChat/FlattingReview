package com.example.flattingreview

import adapters.FeaturedFlatAdapter
import adapters.FeaturedReviewsAdapter
import adapters.PopularFlatAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home_screen.*
import models.Flat
import models.Review
import kotlin.math.round

/**
 * The first screen the user will see when opening the app (after the splash screen). This screen
 * displays a search bar (still to be implemented) and a selection of flats and reviews that
 * users can browse.
 * @author Ryan Cole
 */
class HomeScreen : AppCompatActivity(), PopularFlatAdapter.OnItemClickListener {

    private var featuredFlat: ArrayList<Flat> = ArrayList()
    private var popularFlat: ArrayList<Flat> = ArrayList()
    private var reviewList: ArrayList<Review> = ArrayList()
    private lateinit var flatReference: DatabaseReference
    private lateinit var reviewReference: DatabaseReference
    var ratingList: HashMap<String, ArrayList<Double>> = HashMap()
    private var numberOfReviews: HashMap<String, Int> = HashMap()
    private var layout = "flat_layout"

    /**
     * Creates the references to the database for 'reviews' and 'flats'.
     * Creates the button listeners for dummy flat button (temporary) and
     * the create flat button which takes you to the create flat screen.
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        reviewReference = FirebaseDatabase.getInstance().getReference("reviews")
        flatReference = FirebaseDatabase.getInstance().getReference("flats")

        show_all_button_popular.setOnClickListener {
            val intent = Intent(this, ShowAllFlats::class.java)
            intent.putExtra("list", popularFlat)
            intent.putExtra("ratingList", ratingList)
            startActivity(intent)
        }

        show_all_button_featured.setOnClickListener {
            val intent = Intent(this, ShowAllFlats::class.java)
            intent.putExtra("list", featuredFlat)
            intent.putExtra("ratingList", ratingList)
            startActivity(intent)
        }

        show_all_reviews.setOnClickListener {
            val intent = Intent(this, ShowAllReviews::class.java)
            intent.putExtra("list", reviewList)
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
        getData()
    }

    /**
     * On start it will connect to the database under the reference reviews and flats. And collect all
     * the data for both the flats and reviews to display in the recycler views in the home screen.
     */
    private fun getData() {
        val flatListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Log.w("ViewReview", "loadItem:onCancelled")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val id = ds.child("flatID").value as String
                    val address = ds.child("address").value as String
                    val beds = ds.child("bedrooms").value as String
                    val baths = ds.child("bathrooms").value as String
                    val flat = Flat(id, address, beds, baths)
                    featuredFlat.add(flat)
                    popularFlat.add(flat)
                }
                createViewPopularFlats()
                createViewFeaturedFlats()
            }
        }

        val reviewListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Log.w("ViewReview", "loadItem:onCancelled")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val reviewID = ds.child("reviewID").value as String
                    val userID = ds.child("userID").value as String
                    val flatID = ds.child("flatID").value as String
                    val name = ds.child("name").value as String
                    val clean = ds.child("cleanliness").value as Double
                    val lord = ds.child("landlord").value as Double
                    val location = ds.child("location").value as Double
                    val value = ds.child("value").value as Double
                    val anon = ds.child("anonymous").value as Boolean
                    val date = ds.child("date").value as String
                    val comment = ds.child("comment").value as String

                    val rev = Review(
                        reviewID,
                        userID,
                        flatID,
                        name,
                        clean - 0.1,
                        lord - 0.1,
                        location - 0.1,
                        value - 0.1,
                        anon,
                        date,
                        comment
                    )
                    val ratings: ArrayList<Double> = ArrayList()
                    ratings.add(round((clean + lord + location + value) / 4))
                    ratingList[flatID] = ratings
                    numberOfReviews[flatID] =+ 1
                    if (comment != "") {
                        reviewList.add(rev)
                    }
                }
                createViewFeaturedReviews()
            }
        }
        flatReference.orderByKey().addValueEventListener(flatListener)
        reviewReference.orderByKey().addValueEventListener(reviewListener)
    }

    /**
     * Creates an instance of the FeaturedFlatAdapter for the recycler and passes in
     * a list of flats to display. The layout manager is set to horizontal to display
     * the flats horizontally across the screen
     *
     */
    private fun createViewPopularFlats() {
        popular_flat_recycler.adapter = PopularFlatAdapter(this, featuredFlat, ratingList, this, layout)
        popular_flat_recycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        popular_flat_recycler.setHasFixedSize(true)
    }

    private fun createViewFeaturedFlats() {
        featured_flat_recycler.adapter = FeaturedFlatAdapter(this, featuredFlat, ratingList, this)
        featured_flat_recycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        featured_flat_recycler.setHasFixedSize(true)
    }

    /**
     * Creates an instance of the FeaturedReviewAdapter for the recycler and passes in
     * a list of reviews to display. The layout manager is set to horizontal to display
     * the reviews horizontally across the screen
     *
     */
    private fun createViewFeaturedReviews() {
        featured_reviews_recycler.adapter = FeaturedReviewsAdapter(reviewList)
        featured_reviews_recycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        featured_reviews_recycler.setHasFixedSize(true)
    }

    override fun onItemClick(item: Flat, position: Int){
        val intent = Intent(this, FlatScreen::class.java)
        intent.putExtra("flat", item)
        val array = ratingList[item.flatID]
        var sum = 0.0
        if(!array.isNullOrEmpty()){
            for(i in array) {
                sum += i
            }
        }
        if (array != null) {
            intent.putExtra("overallRating", (sum / array.size).toString())
        } else {
            intent.putExtra("overallRating", "0")
        }
        intent.putExtra("numberOfRatings", numberOfReviews[item.flatID])
        startActivity(intent)
    }
}
