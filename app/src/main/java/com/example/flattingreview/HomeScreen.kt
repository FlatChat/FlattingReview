package com.example.flattingreview

import adapters.FeaturedFlatAdapter
import adapters.FeaturedReviewsAdapter
import adapters.PopularFlatAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home_screen.*
import models.Flat
import models.Review

/**
 * The first screen the user will see when opening the app (after the splash screen). This screen
 * displays a search bar (still to be implemented) and a selection of flats and reviews that
 * users can browse.
 * @author Ryan
 */
class HomeScreen : AppCompatActivity(), PopularFlatAdapter.OnItemClickListener {

    private var featuredFlat: ArrayList<Flat> = ArrayList()
    private var popularFlat: ArrayList<Flat> = ArrayList()
    private var reviewList: ArrayList<Review> = ArrayList()
    private lateinit var flatReference: DatabaseReference
    private lateinit var reviewReference: DatabaseReference
    private var ratingList: HashMap<String, Double> = HashMap()
    private var layout = "flat_layout"
    private val limit: Int = 20
    private lateinit var popularFlatRecycler: RecyclerView
    private lateinit var featuredFlatRecycler: RecyclerView
    private lateinit var reviewRecycler: RecyclerView

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
        reviewReference = FirebaseDatabase.getInstance().reference
        flatReference = FirebaseDatabase.getInstance().reference

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

        popularFlatRecycler = findViewById(R.id.popular_flat_recycler)
        popularFlatRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        popularFlatRecycler.setHasFixedSize(true)
        featuredFlatRecycler = findViewById(R.id.featured_flat_recycler)
        featuredFlatRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        featuredFlatRecycler.setHasFixedSize(true)
        reviewRecycler = findViewById(R.id.featured_reviews_recycler)
        reviewRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        reviewRecycler.setHasFixedSize(true)

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

    private fun getData() {
        val flatListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Log.w("ViewReview", "loadItem:onCancelled")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val flat = ds.getValue(Flat::class.java)
                    if (flat != null) {
                        featuredFlat.add(flat)
                    }
                }
            }
        }

        val popListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Log.w("ViewReview", "loadItem:onCancelled")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val flat = ds.getValue(Flat::class.java)
                    if (flat != null) {
                        popularFlat.add(flat)
                        Log.d("Flat Views:", flat.views.toString())
                    }
                }
                createViewFlats()
            }
        }


        val reviewListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Log.w("ViewReview", "loadItem:onCancelled")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val rev = ds.getValue(Review::class.java)
                    if (rev != null && rev.comment != "") {
                        reviewList.add(rev)
                    }
                }
                createViewReviews()

            }
        }
        flatReference
            .child("flats")
            .orderByChild("views")
            .limitToFirst(limit)
            .addValueEventListener(popListener)
        flatReference
            .child("flats")
            .orderByKey()
            .addValueEventListener(flatListener)
        reviewReference
            .child("reviews")
            .orderByKey()
            .addValueEventListener(reviewListener)
    }

    private fun createViewFlats() {
        popularFlat = popularFlat.reversed() as ArrayList<Flat>
        popularFlatRecycler.adapter =
            PopularFlatAdapter(this, popularFlat, ratingList, this, layout)
        featuredFlatRecycler.adapter =
            FeaturedFlatAdapter(this, featuredFlat, ratingList, this)
    }

    private fun createViewReviews() {
        reviewRecycler.adapter = FeaturedReviewsAdapter(reviewList)
    }


    /**
     * Receives the flat the user has clicked on in the recycler view and opens the
     * flat screen activity, it puts the flat and the rating/review details into the intent
     * for the other activity.
     *
     * @param item the flat that the user has clicked on
     */
    override fun onItemClick(item: Flat){
        val intent = Intent(this, FlatScreen::class.java)
        intent.putExtra("flat", item)
        startActivity(intent)
    }
}
