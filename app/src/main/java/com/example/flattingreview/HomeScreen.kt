package com.example.flattingreview

import adapters.FeatReviewAdapter
import adapters.FeaturedFlatAdapter
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.multidex.MultiDex
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import domain.Flat
import domain.Review
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlin.math.round

/**
 * The first screen the user will see when opening the app (after the splash screen). This screen
 * displays a search bar (still to be implemented) and a selection of flats and reviews that
 * users can browse.
 * @author Ryan Cole
 */
@Suppress("NAME_SHADOWING")
class HomeScreen : AppCompatActivity(), FeaturedFlatAdapter.OnItemClickListener {

    private var featuredFlat: ArrayList<Flat> = ArrayList()
    private var reviewList: ArrayList<Review> = ArrayList()
    private lateinit var flatReference: DatabaseReference
    private lateinit var reviewReference: DatabaseReference
    private var ratingList: HashMap<String, ArrayList<Double>> = HashMap()
    private var numberOfReviews: HashMap<String, Int> = HashMap()

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

        //connecting the create a flat button to the create a new flat screen
        createFlatButton.setOnClickListener {
            val intent = Intent(this, CreateFlat::class.java)
            startActivity(intent)
        }

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.account_screen -> {

                    true
                }
                R.id.home_screen -> {
                    val intent = Intent(this, HomeScreen::class.java)
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
     * On start it will connect to the database under the reference reviews and flats. And collect all
     * the data for both the flats and reviews to display in the recycler views in the homescreen.
     */
    public override fun onStart() {
        super.onStart()
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
                }
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
                    ratings.add(round((clean + lord + location + value - 0.4) / 4))
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
    private fun createViewFeaturedFlats() {
        featured_flat_recycler.adapter = FeaturedFlatAdapter(featuredFlat, ratingList, this)
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
        featured_reviews_recycler.adapter = FeatReviewAdapter(reviewList)
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
            for(item in array) sum += item
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
}
