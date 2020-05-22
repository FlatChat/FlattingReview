package com.example.flattingreview

import adaptors.ReviewAdaptor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import domain.Review
import kotlinx.android.synthetic.main.activity_view_reviews.*

/**
 * Class for presenting the reviews.
 * @author Ryan
 */
class ViewReviews : AppCompatActivity() {

    private lateinit var reviewAdaptor: ReviewAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reviews)

        val myRef = FirebaseDatabase.getInstance().getReference("reviews")



        //initRecyclerView()

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val data = dataSnapshot.getValue<Review>()
                reviewAdaptor.submitList(data)
                //Log.d(TAG, "Value is: $value") the TAG was producing errors
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    /**
     * Sets the layout of the recycling view to linear. Connects this class to the adaptor class
     * which gets the data from the database.
     */
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
