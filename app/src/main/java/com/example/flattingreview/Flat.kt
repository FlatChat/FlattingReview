package com.example.flattingreview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import domain.NewFlat
import domain.Review
import kotlinx.android.synthetic.main.activity_flat.*

/**
 * This is the screen for a particular flat selected
 * by a user (when clicked on from the HomeScreen).
 * It is yet to be connected to the database, but
 * when fully functional will show reviews, images,
 * ratings and comments about a flat.
 * @author Meggie Morrison
 */
class Flat : AppCompatActivity() {

    private var featuredFlat: ArrayList<NewFlat> = ArrayList<NewFlat>()
    private lateinit var flatRef: DatabaseReference
    private var reviewList: ArrayList<Review> = ArrayList<Review>()
    private lateinit var reviewReference: DatabaseReference
    private var flatListener: ValueEventListener? = null

    /**
     * This connects a reference to flats and reviews in the database.
     * It also has button listeners to take the user to other screens.
     *
     * @param  savedInstanceState the most recent state of the application.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flat)

        reviewReference = FirebaseDatabase.getInstance().getReference("reviews")
        flatRef = FirebaseDatabase.getInstance().getReference("flats")

        show_reviews_button.setOnClickListener() {
            val intent = Intent(this, ViewReviews::class.java)
            startActivity(intent)
        }

        add_review_button.setOnClickListener() {
            val intent = Intent(this, WriteReview::class.java)
            startActivity(intent)
        }

        floatingActionButton.setOnClickListener() {
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }

        // Currently isn't connecting to actual flat address:
        // This is only the activity_main flat address
        val addressText: TextView = findViewById<TextView>(R.id.textView)
        addressText.setOnClickListener {
            addressText.text = getString(R.string.flat_address)
        }
    }

    /**
     * This method is not yet fully functional.
     * It will serve to load the data about the
     * selected flat from the database.
     */
    public override fun onStart() {
        super.onStart()
        val flatListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Log.w("Flat", "loadItem:onCancelled")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val address = ds.child("address").value as String
                    val beds = ds.child("bedrooms").value as String
                    val baths = ds.child("bathrooms").value as String
                    val flat = NewFlat(address, beds, baths)
                    featuredFlat.add(flat)
                    //val flat = dataSnapshot.getValue<NewFlat>()
                }
            }
        }
        flatRef.addValueEventListener(flatListener)
    }


    /**
     * The following code is for the action bar.
     * Different options are displayed to take the
     * user to different screens.
     *
     * @param menu the menu file containing the action bar options.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * A method that allows a user to select a specific screen from the action bar.
     *
     * @param item the different action bar options for the user to select.
     * @return the option that the user has selected.
     */
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
