package com.example.flattingreview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.NonNull
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

class CreateFlat : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_flat)

        val apiKey = "AIzaSyBBEQrOBoJ_4UW_E_XOq-8rE-UgoLIlNfo"

        if(!Places.isInitialized()){
            Places.initialize(applicationContext, apiKey)
        }

        var placesClient: PlacesClient = Places.createClient(this)

        val autocompleteSupportFragment: AutocompleteSupportFragment = supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment

        autocompleteSupportFragment.setPlaceFields(listOf(Places.Field.ID, Place.Field.LAT_LNG, Places.Field.NAME))

        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener()){

            override fun onPlacesSelected(@NonNull place: Place){
                val latLng: LatLng = place.getLatLng()
            }

            override fun onError(@NonNull status: Status){

            }

        }

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
