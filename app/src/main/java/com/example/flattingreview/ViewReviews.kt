package com.example.flattingreview

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import domain.Review
import kotlinx.android.synthetic.main.activity_view_reviews.*
import kotlinx.android.synthetic.main.activity_write_review.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt
import kotlin.properties.Delegates

/**
 * Class for presenting the reviews.
 * @author Ryan
 */
class ViewReviews : AppCompatActivity() {

    private var reviewList: ArrayList<Review> = ArrayList<Review>()
    private lateinit var reviewReference: DatabaseReference
    private var reviewListener: ValueEventListener? = null
    private var progress: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reviews)

        reviewReference = FirebaseDatabase.getInstance().getReference("reviews")
        val reviewListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Log.w("ViewReview", "loadItem:onCancelled")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {

                    Log.d("ViewReview", "" + ds.child("cleanliness").value + " : " + ds.child("location").value)

                    val reviewID = ds.child("reviewID").value as String
                    val userID = ds.child("userID").value as String
                    val flatID = ds.child("flatID").value as String
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
                        clean.toFloat(),
                        lord.toFloat(),
                        location.toFloat(),
                        value.toFloat(),
                        anon,
                        date,
                        comment
                    )
                    progress += clean + lord + location + value
                    if(comment != ""){
                        reviewList.add(rev)
                    }
                }
                createView()
                var overallBar = findViewById<ProgressBar>(R.id.overall)
                overallBar.progress = progress.roundToInt()/100
            }
        }
        reviewReference.orderByKey().addValueEventListener(reviewListener)
    }

    private fun createView(){
        recycler_view.adapter = ReviewAdapter(reviewList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
