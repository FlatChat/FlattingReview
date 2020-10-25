package onboarding

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.flattingreview.R
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener


class Address : Fragment(){

    private lateinit var placesClient: PlacesClient
    private lateinit var address: String
    private lateinit var con: Context
    private var myContext: FragmentActivity? = null
    private var placeFields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initPlaces()
        setupPlacesAutoComplete()

        return inflater.inflate(
            R.layout.fragment_first_screen,
            container,
            false
        )
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        con = context
//    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = activity as FragmentActivity
        con = context
    }

    private fun initPlaces(){
        val apiKey = "AIzaSyBBEQrOBoJ_4UW_E_XOq-8rE-UgoLIlNfo"
        Places.initialize(con, apiKey)
        placesClient = Places.createClient(con)
    }

    private fun setupPlacesAutoComplete(){
        val fragManager: FragmentManager = myContext!!.supportFragmentManager
        val autocompleteFragment = fragManager
            .findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autocompleteFragment.setPlaceFields(placeFields)
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(p0: Place) {
                Toast.makeText(con, "" + p0.address, Toast.LENGTH_SHORT).show()
                Log.d("CreateFlat", "" + p0.address)
                address = p0.address.toString()
            }

            override fun onError(p0: Status) {
                Toast.makeText(con, "" + p0.statusCode, Toast.LENGTH_SHORT).show()
            }
        })
    }
}