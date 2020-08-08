package com.example.flattingreview

import adapters.SearchAdapter
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_search.*
import models.Flat
import models.Review
import kotlin.math.round

class Search : AppCompatActivity(), SearchAdapter.OnItemClickListener {

    private lateinit var mSearchText : EditText
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var flatReference: DatabaseReference
    private lateinit var reviewReference: DatabaseReference
    private var flatList: ArrayList<Flat> = ArrayList()
    private var ratingList: HashMap<String, ArrayList<Double>> = HashMap()
    private var numberOfReviews: HashMap<String, Int> = HashMap()
    private var reviewList: ArrayList<Review> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        reviewReference = FirebaseDatabase.getInstance().getReference("reviews")
        flatReference = FirebaseDatabase.getInstance().getReference("flats")

        mSearchText = findViewById(R.id.search_bar_text)
        mRecyclerView = findViewById(R.id.search_view_recycler)

        // Bottom navigation
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.search_screen
        bottom_navigation.setOnNavigationItemSelectedListener {
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

        mSearchText.addTextChangedListener(object  : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = mSearchText.text.toString().trim()
                firebaseSearch(searchText)
            }
        })

    }

    private fun firebaseSearch(searchText : String) {

        if(searchText.isEmpty()){

//            FirebaseRecyclerAdapter.cleanup()
//            mRecyclerView.adapter = FirebaseRecyclerAdapter

        } else {

            val query: Query = flatReference.orderByChild("address").startAt(searchText).endAt(searchText + "\uf8ff")

            query.addChildEventListener(object : ChildEventListener{
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    val id = snapshot.child("flatID").value as String
                    val address = snapshot.child("address").value as String
                    val beds = snapshot.child("bedrooms").value as String
                    val baths = snapshot.child("bathrooms").value as String
                    val flat = Flat(id, address, beds, baths)
                    flatList.add(flat)
                    createViewSearchFlats()
                }

                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val id = snapshot.child("flatID").value as String
                    val address = snapshot.child("address").value as String
                    val beds = snapshot.child("bedrooms").value as String
                    val baths = snapshot.child("bathrooms").value as String
                    val flat = Flat(id, address, beds, baths)
                    flatList.add(flat)
                    createViewSearchFlats()
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {

                }

            })

//            val flatListener: ValueEventListener = object : ValueEventListener {
//                override fun onCancelled(dataSnapshot: DatabaseError) {
//                    Log.w("ViewReview", "loadItem:onCancelled")
//                }
//
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    for (ds in dataSnapshot.children) {
//                        val id = ds.child("flatID").value as String
//                        val address = ds.child("address").value as String
//                        val beds = ds.child("bedrooms").value as String
//                        val baths = ds.child("bathrooms").value as String
//                        val flat = Flat(id, address, beds, baths)
//                        flatList.add(flat)
//                    }
//                    createViewSearchFlats()
//                }
//            }

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
                        numberOfReviews[flatID] = +1
                        if (comment != "") {
                            reviewList.add(rev)
                        }
                    }
                }
            }
//            flatReference.orderByKey().addValueEventListener(flatListener)
            reviewReference.orderByKey().addValueEventListener(reviewListener)
        }
    }

    /**
     * Creates an instance of the FeaturedFlatAdapter for the recycler and passes in
     * a list of flats to display. The layout manager is set to horizontal to display
     * the flats horizontally across the screen
     *
     */
    private fun createViewSearchFlats() {
        search_view_recycler.adapter = SearchAdapter(flatList, ratingList, this)
        search_view_recycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        search_view_recycler.setHasFixedSize(true)
    }

     override fun onItemClick(item: Flat, position: Int){
        val intent = Intent(this, FlatScreen::class.java)
        intent.putExtra("flat", item)
        val array = ratingList[item.flatID]
        var sum = 0.0
        if(!array.isNullOrEmpty()){
            for(i in array) sum += i
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

