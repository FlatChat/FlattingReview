@file:Suppress("PrivatePropertyName")

package com.flatchat.app

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_create_new_flat.*
import kotlinx.android.synthetic.main.activity_home_screen.bottom_navigation
import models.Flat
import java.io.ByteArrayOutputStream


/**
 * This class creates new flat objects and saves them to the database.
 * It also incorporates google places so that a user can look up the
 * address they are entering, and the address may be auto-filled.
 * @author Ryan
 * @author Meggie Morrison
 */

class CreateFlat : AppCompatActivity() {

    private lateinit var placesClient: PlacesClient
    private lateinit var address: String
    private lateinit var bedrooms: Editable
    private lateinit var bathrooms: Editable
    private lateinit var createButton: Button
    private val REQUEST_IMAGE_CAPTURE = 1
    private var placeFields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS)

    /**
     * This is the driver code for collecting
     * information about the flat and then adding
     * it to the database.
     *
     * @param  savedInstanceState the most recent state of the application.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_flat)
        initPlaces()
        setupPlacesAutoComplete()
        createButton = findViewById(R.id.create_flat)
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.add_flat_screen

        create_flat.setOnClickListener {
            collectInput()
            if(this::address.isInitialized && this::bathrooms.isInitialized && this::bedrooms.isInitialized){
                writeNewFlat()
            } else {
                if(!this::address.isInitialized){
                    Toast.makeText(this, "Forgot to add the address", Toast.LENGTH_LONG).show()
                }
                if(!this::bathrooms.isInitialized){
                    Toast.makeText(this, "Forgot to add the bathrooms", Toast.LENGTH_LONG).show()
                }
                if(!this::bedrooms.isInitialized){
                    Toast.makeText(this, "Forgot to add the bedrooms", Toast.LENGTH_LONG).show()
                }
            }
        }

        // Bottom navigation
        bottomNavigation.selectedItemId = R.id.add_flat_screen
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.account_screen -> {
                    val intent = Intent(this, Settings::class.java)
                    startActivity(intent)
                    true
                }
                R.id.home_screen -> {
                    val intent = Intent(this, HomeScreen::class.java)
                    startActivity(intent)
                    true
                }
                R.id.search_screen -> {
                    val intent = Intent(this, Search::class.java)
                    startActivity(intent)
                    true
                }
                R.id.add_flat_screen -> {
                    true
                }
                else -> false
            }
        }

        //BUTTON CLICK
        upload_image.setOnClickListener {
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE)
                }
                else{
                    //permission already granted
                    pickImageFromGallery()
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery()
            }
        }

        camera_button.setOnClickListener {
            dispatchTakePictureIntent()
        }

    }



    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private const val IMAGE_PICK_CODE = 1000
        //Permission code
        private const val PERMISSION_CODE = 1001
    }

    /**
     * Handles the request permissions.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * Handles the results of the picked image.
     *
     * @param requestCode
     * @param resultCode
     * @param data The image
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            if (data != null) {
                upload_flat_image.setImageURI(data.data)
            } else {
                Toast.makeText(this, "Error Uploading Photo", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            upload_flat_image.setImageBitmap(imageBitmap)
        } else {
            Toast.makeText(this, "Error Uploading Photo", Toast.LENGTH_SHORT).show()
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
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
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(p0: Place) {
                Toast.makeText(this@CreateFlat, "" + p0.address, Toast.LENGTH_SHORT).show()
                Log.d("CreateFlat", "" + p0.address)
                address = p0.address.toString()
                fillAddressBoxes(address)
            }

            override fun onError(p0: Status) {
                Toast.makeText(this@CreateFlat, "" + p0.statusCode, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fillAddressBoxes(address: String){
        val list = address.split(",")
        val street = list[0]
        val suburb = list[1]
        val city = list[2].split(" ")
        val streetText: TextView = findViewById<EditText>(R.id.street)
        val suburbText: TextView = findViewById(R.id.suburb)
        val cityText : TextView = findViewById(R.id.city)
        streetText.text = street
        suburbText.text = suburb
        cityText.text = city[1]
    }

    private fun collectInput(){
        bedrooms = findViewById<EditText>(R.id.bedrooms).text
        bathrooms = findViewById<EditText>(R.id.bathrooms).text
    }

    private fun writeNewFlat(){
        val myRef = FirebaseDatabase.getInstance().getReference("flats")
        val flatID = myRef.push().key
        val imageID = "image$flatID"
        val storageRef = Firebase.storage.reference.child("flats/$imageID.jpg")
        val bitmap = (upload_flat_image.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val flat = Flat(
            flatID,
            address,
            bedrooms.toString(),
            bathrooms.toString(),
            0
        )
        myRef.child(flatID.toString()).setValue(flat)

        val uploadTask = storageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Log.d("File Upload", "Failure")
        }.addOnSuccessListener {
            Log.d("File Upload", "Successful")
        }

        val intent = Intent(this, HomeScreen::class.java)
        startActivity(intent)
    }
}