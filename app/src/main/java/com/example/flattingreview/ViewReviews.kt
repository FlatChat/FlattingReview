package com.example.flattingreview

import adapters.ReviewAdapter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import domain.Review
import kotlinx.android.synthetic.main.activity_view_reviews.*
import kotlin.collections.ArrayList

/**
 * This will display all the written reviews for a particular flat. This page is navigated to
 * by clicking 'show all reviews' form the Flat.kt screen.
 * @author Ryan
 */
class ViewReviews : AppCompatActivity() {

    private var reviewList: ArrayList<Review> = ArrayList<Review>()
    private lateinit var reviewReference: DatabaseReference
    private var reviewListener: ValueEventListener? = null
    private var adapter: ReviewAdapter? = null

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
                    if(comment != ""){
                        reviewList.add(rev)
                    }
                }
                createView()
            }
        }
        reviewReference.orderByKey().addValueEventListener(reviewListener)
    }

    /**
     * The createView() method creates an instance of the ReviewAdapter adn sends
     * a list of the reviews to display. It also sets the layout manager and to the recycler
     * size to fixed.
     *
     */
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
