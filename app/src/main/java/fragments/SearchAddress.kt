package fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flattingreview.CreateFlat
import com.example.flattingreview.R
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

/**
 * @author Ryan
 *
 */
class SearchAddress : Fragment() {

    private lateinit var placesClient: PlacesClient
    private lateinit var address: String
    private var placeFields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS)

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        initPlaces()
//        setupPlacesAutoComplete()
//    }
//
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_address, container, false)
    }

//    /**
//     * Initialise google places by using the apiKey.
//     * Creates a places client.
//     */
//    private fun initPlaces(){
//        val apiKey = "AIzaSyBBEQrOBoJ_4UW_E_XOq-8rE-UgoLIlNfo"
//        Places.initialize(this, apiKey)
//        placesClient = Places.createClient(this)
//    }
//
//    /**
//     * Implements a google places autocomplete into a fragment.
//     * The listener is for when a location is selected, and you use it to
//     * pull the data from the fragment.
//     */
//    private fun setupPlacesAutoComplete(){
//        val autocompleteFragment = supportFragmentManager
//            .findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
//        autocompleteFragment.setPlaceFields(placeFields)
//        autocompleteFragment.setOnPlaceSelectedListener(object: PlaceSelectionListener {
//            override fun onPlaceSelected(p0: Place) {
//                Toast.makeText(this@SearchAddress, ""+ p0.address, Toast.LENGTH_SHORT).show()
//                Log.d("CreateFlat", "" + p0.address)
//                address = p0.address.toString()
//            }
//
//            override fun onError(p0: Status) {
//                Toast.makeText(this@SearchAddress, ""+ p0.statusCode, Toast.LENGTH_SHORT).show()
//            }
//        })
//    }

}