package com.example.flattingreview

import adapters.ReviewAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
//import firebase.Connect
import kotlinx.android.synthetic.main.activity_view_reviews.*
import models.Flat
import models.Review

/**
 * This will display all the written reviews for a particular flat. This page is navigated to
 * by clicking 'show all reviews' form the Flat.kt screen.
 * @author Ryan
 */
@Suppress("NAME_SHADOWING")
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

//        val connect: Connect = Connect()
//        connect.getReviewByFlat(flat)
//        reviewList.addAll(connect.reviewList)


        val reviewListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Log.w("ViewReview", "loadItem:onCancelled")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    if (flatID == ds.child("flatID").value as String) {
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



    /**
     * The createView() method creates an instance of the ReviewAdapter adn sends
     * a list of the reviews to display. It also sets the layout manager and to the recycler
     * size to fixed.
     *
     */
    private fun createView() {
        recycler_view.adapter = ReviewAdapter(reviewList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        //If home screen option is pressed go to home screen
        if (id == R.id.home_screen) {
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }
        //If write a review option is pressed go to review screen
        if (id == R.id.write_review) {
            val intent = Intent(this, WriteReview::class.java)
            startActivity(intent)
        }
        //If contact us option is pressed go to contact us screen
        if (id == R.id.contact) {
            val intent = Intent(this, Account::class.java)
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
