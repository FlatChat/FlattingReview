package com.example.flattingreview

import adapters.ReviewAdapter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import models.Flat
import models.Review
import kotlin.collections.ArrayList

/**
 * This will display all the written reviews for a particular flat. This page is navigated to
 * by clicking 'show all reviews' form the Flat.kt screen.
 * @author Ryan
 */
class ViewReviews : AppCompatActivity() {

    private var reviewList: ArrayList<Review> = ArrayList()
    private lateinit var reviewReference: DatabaseReference


    /**
     * Sets the database reference and collects the path to which the reviews and read from.
     *
     * On start this method will connect to the database under the 'reviews' path and
     * read all the reviews that are written about a particular flat. It creates a model
     * review for each one that is read from the database and adds them to a list of
     * reviews. When all the reviews are added to the list it calls the createView()
     * method.
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reviews)
        reviewReference = FirebaseDatabase.getInstance().getReference("reviews")
//        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.setHasFixedSize(true)

        val flat = intent.getSerializableExtra("flat") as Flat
        val flatID = flat.flatID



        val reviewListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Log.w("ViewReview", "loadItem:onCancelled")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    if (flatID == ds.child("flatID").value as String) {
                        val rev = ds.getValue(Review::class.java)
                        if (rev != null) {
                            if (rev.comment != "") {
                                reviewList.add(rev)
                            }
                        }
                    }
                }
                if(reviewList.size != 0) {
                    createView()
                }
            }
        }
        reviewReference.orderByKey().addValueEventListener(reviewListener)

    }


    private fun createView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = ReviewAdapter(this, reviewList)
    }
}
