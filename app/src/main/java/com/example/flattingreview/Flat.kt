package com.example.flattingreview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


// Write a message to the database
private val database = Firebase.database
private val myRef = database.getReference("message")
import kotlinx.android.synthetic.main.activity_flat.*

class Flat : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flat)

        show_reviews_button.setOnClickListener(){
            val intent = Intent(this,ViewReviews::class.java)
            startActivity(intent)
        }

        add_review_button.setOnClickListener(){
            val intent = Intent(this,WriteReview::class.java)
            startActivity(intent)
        }

/**
        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val info = dataSnapshot.getValue<NewFlat>()
                //Log.d(TAG, "Value is: $value") the TAG was producing errors
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

**/
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}
