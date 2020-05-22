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

class CreateFlat : AppCompatActivity() {

    private lateinit var placesClient: PlacesClient

    private var placeFields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_flat)
        initPlaces()
        setupPlacesAutoComplete()
    }

    private fun initPlaces(){
        val apiKey = "AIzaSyBBEQrOBoJ_4UW_E_XOq-8rE-UgoLIlNfo"
        Places.initialize(this, apiKey)
        placesClient = Places.createClient(this)
    }

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
        if(id==R.id.logout)
        {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
