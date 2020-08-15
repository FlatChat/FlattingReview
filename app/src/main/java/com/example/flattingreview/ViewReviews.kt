package com.example.flattingreview

import adapters.ReviewAdapter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_view_reviews.*
import models.Flat
import models.Review

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
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reviews)
        reviewReference = FirebaseDatabase.getInstance().getReference("reviews")

    }

    /**
     * On start this method will connect to the database under the 'reviews' path and
     * read all the reviews that are written about a particular flat. It creates a model
     * review for each one that is read from the database and adds them to a list of
     * reviews. When all the reviews are added to the list it calls the createView()
     * method.
     *
     */
    public override fun onStart() {
        super.onStart()

        val flat = intent.getSerializableExtra("flat") as Flat
        val flatID = flat.flatID


        val reviewListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Log.w("ViewReview", "loadItem:onCancelled")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    if (flatID == ds.child("flatID").value as String) {
                        val reviewID = ds.child("reviewID").value as String
                        val userID = ds.child("userID").value as String
                        val id = ds.child("flatID").value as String
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
                            id,
                            name,
                            clean - 0.1,
                            lord - 0.1,
                            location - 0.1,
                            value - 0.1,
                            anon,
                            date,
                            comment
                        )
                        if (comment != "") {
                            reviewList.add(rev)
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
        recycler_view.adapter = ReviewAdapter(reviewList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
    }
}
