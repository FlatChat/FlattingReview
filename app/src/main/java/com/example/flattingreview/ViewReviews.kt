package com.example.flattingreview

import adaptors.ReviewAdaptor
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import domain.Review
import kotlinx.android.synthetic.main.activity_view_reviews.*

// Write a message to the database
private val database = Firebase.database
private val myRef = database.getReference("message")

class ViewReviews : AppCompatActivity() {

    private lateinit var reviewAdaptor: ReviewAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reviews)

        initRecyclerView()

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<Review>()
                //Log.d(TAG, "Value is: $value") the TAG was producing errors
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    /**
     * Use this function to get the data from the database and submit it
     * with reviewAdaptor.submitList(data). Data must be:
     * data = ...
     */
    private fun addDataSet(){

    }

    private fun initRecyclerView(){
        review_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@ViewReviews)
            reviewAdaptor = ReviewAdaptor()
            adapter = reviewAdaptor
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}
