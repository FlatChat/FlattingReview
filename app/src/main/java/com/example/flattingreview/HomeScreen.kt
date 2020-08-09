package com.example.flattingreview

import adapters.FeaturedFlatAdapter
import adapters.FeaturedReviewsAdapter
import adapters.PopularFlatAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.multidex.MultiDex
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
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
    private var ratingList: HashMap<String, ArrayList<Double>> = HashMap()
    private var numberOfReviews: HashMap<String, Int> = HashMap()
    private lateinit var flatReference: DatabaseReference
    private lateinit var reviewReference: DatabaseReference
    private lateinit var rvFeaturedFlats: RecyclerView
    private lateinit var rvPopularFlats: RecyclerView
    private lateinit var rvReviews: RecyclerView

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

        setUpRecyclerViews()
        getData()
        fillRecyclerViews()

        // Bottom navigation
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation_home)
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
    }

    /**
     * This method is called to set up the layout of the recycler views, this has to be done
     * separately to the loading of the data because of the latency to get data from firebase.
     */
    private fun setUpRecyclerViews(){
        rvFeaturedFlats = findViewById(R.id.featured_flat_recycler)
        rvPopularFlats = findViewById(R.id.popular_flat_recycler)
        rvReviews = findViewById(R.id.featured_reviews_recycler)
        rvFeaturedFlats.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvPopularFlats.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvReviews.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvFeaturedFlats.setHasFixedSize(true)
        rvPopularFlats.setHasFixedSize(true)
        rvReviews.setHasFixedSize(true)
    }

//    fun fetchData(){
//        val connect = Connect()
//        reviewList = connect.reviewList
//        numberOfReviews = connect.numberOfReviews
//        ratingList = connect.ratingList
//        featuredFlat = connect.flats
//        popularFlat = connect.flats
//    }
//

    /**
     * When the data has been retrieved from firebase, this method will be called to load the
     * the data into the recycler views. Each recycler view is called and the appropriate list
     * is feed in.
     */
    private fun fillRecyclerViews(){
        rvFeaturedFlats.adapter = FeaturedFlatAdapter(this, featuredFlat, ratingList, this)
        rvPopularFlats.adapter = FeaturedFlatAdapter(this, featuredFlat, ratingList, this)
        rvReviews.adapter = FeaturedReviewsAdapter(reviewList)
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
                fillRecyclerViews()
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
                    ratings.add(round((clean + lord + location + value - 0.4) / 4))
                    ratingList[flatID] = ratings
                    numberOfReviews[flatID] =+ 1
                    if (comment != "") {
                        reviewList.add(rev)
                    }
                }
                fillRecyclerViews()
            }
        }
        flatReference.orderByKey().addValueEventListener(flatListener)
        reviewReference.orderByKey().addValueEventListener(reviewListener)
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

    /**
     * To allow the project to increase its size over 64kb when its built.
     *
     * @param base
     */
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
