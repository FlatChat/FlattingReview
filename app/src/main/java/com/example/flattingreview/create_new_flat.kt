package com.example.flattingreview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase

/**
 * This class creates new flats and adds them to the database
 * @author Meggie Morrison
 */
class create_new_flat : AppCompatActivity() {


    private lateinit var address: Editable
    // Bedrooms and bathrooms are optional
    private lateinit var bedrooms: Editable
    private lateinit var bathrooms: Editable
    private lateinit var createButton: Button

    /**
     * This is the driver code for collecting
     * information about the flat and then adding
     * it to the database
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_flat)

        collectInput()

        createButton.setOnClickListener {
            //myRef.setValue(flat)
            writeNewFlat()
        }
    }

    /**
     * This function takes user input from the
     * create_new_flat activity, and stores
     * it into variables
     */
    private fun collectInput(){
        address = findViewById<EditText>(R.id.addressBox).text
        bedrooms = findViewById<EditText>(R.id.bedroomBox).text
        bathrooms = findViewById<EditText>(R.id.bathroomBox).text
        createButton = findViewById(R.id.createButton)
    }

    /**
     * This function writes the New Flat
     * to the database
     */
    private fun writeNewFlat(){
        // Database reference
        val myRef = FirebaseDatabase.getInstance().getReference("flats")
        // Creates the flatID
        val flatID = myRef.push().key
        // Creates a flat object
        val flat = NewFlat(address.toString(), bedrooms.toString(), bathrooms.toString())
        // Writes the flat to the database
        myRef.child(flatID.toString()).setValue(flat)

    }

    // Below code is for the action bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

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
        if(id==R.id.logout)
        {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }


}
