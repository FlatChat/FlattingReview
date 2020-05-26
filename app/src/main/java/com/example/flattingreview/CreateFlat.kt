package com.example.flattingreview

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener


/**
 * Creates a flat: a user can look up the address using google places, the address is created
 * into a flat object which is then saved into the database.
 * @author Ryan
 */
class CreateFlat : AppCompatActivity() {

    private lateinit var placesClient: PlacesClient

    // Attributes to store from the address that the user chooses
    private var placeFields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_flat)
        initPlaces()
        setupPlacesAutoComplete()
    }

    /**
     * Initialise google places by using the apiKey.
     * Creates a places client.
     */
    private fun initPlaces(){
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
        autocompleteFragment.setOnPlaceSelectedListener(object:PlaceSelectionListener {
            override fun onPlaceSelected(p0: Place) {
                Toast.makeText(this@CreateFlat, ""+ p0.address, Toast.LENGTH_SHORT).show()
                TODO("create the flat object here, I think you use the p0.address somehow and turn" +
                        "it into a flat object, once that's done store it in the database")
            }

            override fun onError(p0: Status) {
                Toast.makeText(this@CreateFlat, ""+ p0.statusCode, Toast.LENGTH_SHORT).show()
            }
        })
    }


    //below code is for the action bar
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
        if(id==R.id.settings)
        {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
