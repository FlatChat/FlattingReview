package com.example.flattingreview

import adapters.FeatReviewAdapter
import adapters.FeaturedFlatAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.multidex.MultiDex
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import domain.NewFlat
import domain.Review
import kotlinx.android.synthetic.main.activity_home_screen.*

/**
 * Homescreen that the user will first when opening the app
 * @author Ryan
 */
class HomeScreen : AppCompatActivity() {

    private var featuredFlat: ArrayList<NewFlat> = ArrayList<NewFlat>()
    private lateinit var homeScreenReference: DatabaseReference
    private var reviewList: ArrayList<Review> = ArrayList<Review>()
    private lateinit var reviewReference: DatabaseReference
    private var reviewAdapter: FeatReviewAdapter? = null
    private var flatAdapter: FeaturedFlatAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
         reviewReference = FirebaseDatabase.getInstance().getReference("reviews")
        homeScreenReference = FirebaseDatabase.getInstance().getReference("flats")

        dummy_flat.setOnClickListener{
            val intent = Intent(this,Flat::class.java)
            startActivity(intent)
        }

        //connecting the create a flat button to the create a new flat screen
        createFlatButton.setOnClickListener{
            val intent = Intent(this,CreateFlat::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
        val homeScreenListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Log.w("ViewReview", "loadItem:onCancelled")
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val address = ds.child("address").value as String
                    val beds = ds.child("bedrooms").value as String
                    val baths = ds.child("bathrooms").value as String
                    val flat = NewFlat(address, beds, baths)
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
                    val clean = ds.child("cleanliness").value as Long
                    val lord = ds.child("landlord").value as Long
                    val location = ds.child("location").value as Long
                    val value = ds.child("value").value as Long
                    val anon = ds.child("anonymous").value as Boolean
                    val date = ds.child("date").value as String
                    val comment = ds.child("comment").value as String

                    val rev = Review(
                        reviewID,
                        userID,
                        flatID,
                        name,
                        clean.toFloat(),
                        lord.toFloat(),
                        location.toFloat(),
                        value.toFloat(),
                        anon,
                        date,
                        comment
                    )
                    if(comment != ""){
                        reviewList.add(rev)
                    }
                }
                createViewFeaturedReviews()
            }
        }
        homeScreenReference.orderByKey().addValueEventListener(homeScreenListener)
        reviewReference.orderByKey().addValueEventListener(reviewListener)
    }

    private fun createViewFeaturedFlats(){
        featured_flat_recycler.adapter = FeaturedFlatAdapter(featuredFlat)
        featured_flat_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        featured_flat_recycler.setHasFixedSize(true)
    }

    private fun createViewFeaturedReviews(){
        featured_reviews_recycler.adapter = FeatReviewAdapter(reviewList)
        featured_reviews_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        featured_reviews_recycler.setHasFixedSize(true)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    //below code is all for the action bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId

        //If home screen option is pressed go to home screen
        if(id==R.id.homescreen)
        {
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }
        //If write a review option is pressed go to review screen
        if(id==R.id.writereview)
        {
            val intent = Intent(this, WriteReview::class.java)
            startActivity(intent)
        }
        //If contact us option is pressed go to contact us screen
        if(id==R.id.contact)
        {
            val intent = Intent(this, Contact::class.java)
            startActivity(intent)
        }
        //If logout option is selected then redirect user to the login screen
        if(id==R.id.settings)
        {
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
