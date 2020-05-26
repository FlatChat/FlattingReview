package com.example.flattingreview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.firebase.database.FirebaseDatabase

/**
 * This class creates new flats and adds them to the database
 * @author Meggie Morrison
 * Creates a flat: a user can look up the address using google places, the address is created
 * into a flat object which is then saved into the database.
 * @author Ryan
 */
class create_new_flat : AppCompatActivity() {

    private lateinit var placesClient: PlacesClient

    private lateinit var address: Editable
    // Bedrooms and bathrooms are optional
    private lateinit var bedrooms: Editable
    private lateinit var bathrooms: Editable
    private lateinit var createButton: Button


    // Attributes to store from the address that the user chooses
    private var placeFields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS)

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
     * Initialise google places by using the apiKey.
     * Creates a places client.
     */
    private fun initPlaces() {
        Places.initialize(this, R.string.apiKey.toString())
        placesClient = Places.createClient(this)
    }

    /**
     * Implements an google places autocomplete into a fragment.
     * Listener is for when a location is selected, you use the listener to pull the data
     * from the fragment.
     */
    private fun setupPlacesAutoComplete(){
        val autocompleteFragment = supportFragmentManager
            .findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autocompleteFragment.setPlaceFields(placeFields)
        autocompleteFragment.setOnPlaceSelectedListener(object: PlaceSelectionListener {
            override fun onPlaceSelected(p0: Place) {
                Toast.makeText(this@create_new_flat, ""+ p0.address, Toast.LENGTH_SHORT).show()
                TODO("create the flat object here, I think you use the p0.address somehow and turn" +
                        "it into a flat object, once that's done store it in the database")
            }

            override fun onError(p0: Status) {
                Toast.makeText(this@create_new_flat, ""+ p0.statusCode, Toast.LENGTH_SHORT).show()
            }
        })
    }

        /**
     * This function takes user input from the
     * create_new_flat activity, and stores
     * it into variables
     */
    private fun collectInput(){
        //address = findViewById<EditText>(R.id.addressBox).text
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
