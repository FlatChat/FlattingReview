package com.example.flattingreview

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import domain.Flat
import domain.Review
import kotlinx.android.synthetic.main.activity_write_review.*
import java.util.Date

/**
 * Class designed to collect the data inputted by the users from activity_write_review.xml
 * and write the data into the database.
 * @author Ryan
 */
class WriteReview : AppCompatActivity(), RatingBar.OnRatingBarChangeListener {

    private lateinit var submitButton: Button
    private lateinit var cleanliness: RatingBar
    private lateinit var landlord: RatingBar
    private lateinit var location: RatingBar
    private lateinit var value: RatingBar
    private lateinit var anon: Switch
    private var comment: Editable? = null
    private lateinit var userReference: DatabaseReference
    private lateinit var flatReference: DatabaseReference
    private lateinit var reviewReference: DatabaseReference
    private var name: String? = null
    private var userID = FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var flat: Flat

    /**
     * The onCreate method calls the setInputs() method which sets all the
     * input elements to a variable. This method also has the cancel button which takes
     * you back to the Flat.kt screen and the submit submit button that will call the
     * saveObject() method.
     *
     * @param savedInstanceState
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_review)

        userReference = FirebaseDatabase.getInstance().getReference("users")
        reviewReference = FirebaseDatabase.getInstance().getReference("reviews")

        flat = intent.getSerializableExtra("flat") as Flat
        setDisplay()
        setInput()
        submitButton.setOnClickListener {
            saveObject()
            val intent = Intent(this, FlatScreen::class.java)
            intent.putExtra("flat", flat)
            startActivity(intent)
        }
        cancel.setOnClickListener() {
            val intent = Intent(this, FlatScreen::class.java)
            intent.putExtra("flat", flat)
            startActivity(intent)
        }
    }

    private fun setDisplay(){
        val address = flat.address
        val addressText: TextView = findViewById(R.id.write_review_address)
        addressText.text = address!!.split(",")[0]
    }


    /**
     * Will set the input elements to variables, enabling collection of the users input
     */
    private fun setInput() {
        submitButton = findViewById(R.id.submit_button)
        comment = findViewById<EditText>(R.id.comment1).text
        cleanliness = findViewById(R.id.cleanliness)
        landlord = findViewById(R.id.landlord)
        location = findViewById(R.id.location)
        value = findViewById(R.id.value)
        anon = findViewById(R.id.anonSwitch)
    }

    /**
     * On start will connect to the database under the 'users' path and collect the first name
     * of the currently signed in user. In stores the first name in the variable name.
     */
    public override fun onStart() {
        super.onStart()
        val flatID = flat.flatID
        val userListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.w("WriteReview", "loadItem:onCancelled")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val u = ds.child("userID").value as String
                    if (u == userID) {
                        name = ds.child("firstNameUsers").value as String
                    }
                }
            }
        }
        userReference.orderByKey().addValueEventListener(userListener)
    }

    /**
     * Gets the FlatID and UserID to attach to the review. Records a time stamp of when the
     * review was created. Get the firebase reference to save the data into. Creates a reviewID
     * through the firebase id system. Creates a reviews object. Writes the review object into
     * the database.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveObject() {
        val flatID = flat.flatID
        // When the review was created
        val sdf = android.icu.text.SimpleDateFormat("dd MMMM yyyy")
        val currentDate = sdf.format(Date())
        // Creates reviewID
        val reviewID = reviewReference.push().key
        // Create a review object
        val rev = Review(
            reviewID,
            userID,
            flatID,
            name.toString(),
            cleanliness.rating + 0.1,
            landlord.rating + 0.1,
            location.rating + 0.1,
            value.rating + 0.1,
            anon.isChecked,
            currentDate,
            comment.toString()
        )
        // Writes into database
        reviewReference.child(reviewID.toString()).setValue(rev)
    }


    //below code is all for the action bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        //If home screen option is pressed go to home screen
        if (id == R.id.homescreen) {
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }
        //If write a review option is pressed go to review screen
        if (id == R.id.writereview) {
            val intent = Intent(this, WriteReview::class.java)
            startActivity(intent)
        }
        //If contact us option is pressed go to contact us screen
        if (id == R.id.contact) {
            val intent = Intent(this, Contact::class.java)
            startActivity(intent)
        }
        //If logout option is selected then redirect user to the login screen
        if (id == R.id.settings) {
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {
        TODO("Not yet implemented")
    }
}

