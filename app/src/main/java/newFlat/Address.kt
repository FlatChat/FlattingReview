package newFlat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.flatchat.app.HomeScreen
import com.flatchat.app.R
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import newFlat.NewFlat.Flat.address

class Address : Fragment(){

    private lateinit var placesClient: PlacesClient
    private var placeFields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS)

    /**
     * The commented out code is for making my own design box, I'm halfway through the process.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(
            R.layout.fragment_address,
            container,
            false
        )

        val backButton = view.findViewById<Button>(R.id.button_back)
        val nextButton = view.findViewById<Button>(R.id.button_next)
//      val searchBar = view.findViewById<AutoCompleteTextView>(R.id.search_bar)
//
//        searchBar.addTextChangedListener(object : TextWatcher {
//
//            override fun afterTextChanged(s: Editable) {}
//
//            override fun beforeTextChanged(s: CharSequence, start: Int,
//                                           count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence, start: Int,
//                                       before: Int, count: Int) {
//                val list = setupPlacesAutoComplete(s.toString()) as ArrayList
//
//                val adapter = ArrayAdapter(requireActivity(),
//                    android.R.layout.simple_list_item_1, list)
//                searchBar.setAdapter(PlacesAutoCompleteAdapter)
//            }
//        })

        backButton?.setOnClickListener{
            val intent = Intent(activity, HomeScreen::class.java)
                startActivity(intent)
        }

        nextButton?.setOnClickListener{
            if(address != ""){
                (activity as NewFlat?)!!.setFragment(1)
            } else {
                incomplete()
            }

        }

        initPlaces()
        setupPlacesAutoComplete()

        return view
    }

    private fun incomplete(){
        val searchBar = view?.findViewById<CardView>(R.id.search_bar_container)
        searchBar?.background = ResourcesCompat.getDrawable(resources, R.drawable.button_red_outline, null)
    }

    private fun complete(){
        val searchBar = view?.findViewById<CardView>(R.id.search_bar_container)
        searchBar?.background = ResourcesCompat.getDrawable(resources, R.drawable.button_rounded, null)
    }

    private fun initPlaces(){
        val apiKey = "AIzaSyBBEQrOBoJ_4UW_E_XOq-8rE-UgoLIlNfo"
        Places.initialize(requireActivity(), apiKey)
        placesClient = Places.createClient(requireActivity())
    }

    private fun setupPlacesAutoComplete(){

//        val addressList: ArrayList<String>? = null
//
//        val token = AutocompleteSessionToken.newInstance()
//        val bounds = RectangularBounds.newInstance(
//            LatLng(-45.938747, 170.581089),
//            LatLng(-45.828810, 170.377706)
//        )
//
//        val request =
//            FindAutocompletePredictionsRequest.builder()
//                // Call either setLocationBias() OR setLocationRestriction().
//                .setLocationBias(bounds)
//                //.setLocationRestriction(bounds)
//                .setOrigin(LatLng(-45.8749937, 170.4041382))
//                .setCountries("NZ")
//                .setTypeFilter(TypeFilter.ADDRESS)
//                .setSessionToken(token)
//                .setQuery(query)
//                .build()
//
//        placesClient.findAutocompletePredictions(request)
//            .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
//                for (prediction in response.autocompletePredictions) {
//                    Log.i(TAG, prediction.placeId)
//                    Log.i(TAG, prediction.getPrimaryText(null).toString())
//                    addressList?.add(prediction.getPrimaryText(null).toString())
//                }
//            }.addOnFailureListener { exception: Exception? ->
//                if (exception is ApiException) {
//                    Log.e(TAG, "Place not found: " + exception.statusCode)
//                }
//            }
//
//        return addressList

        val autocompleteFragment = childFragmentManager
            .findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autocompleteFragment.setPlaceFields(placeFields)
        autocompleteFragment.setLocationBias(
            RectangularBounds.newInstance(
                LatLng(-45.938747, 170.581089),
                LatLng(-45.828810, 170.377706)
            )
        )
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(p0: Place) {
                address = p0.address.toString()
                complete()
            }

            override fun onError(p0: Status) {
                Toast.makeText(activity, "Failed try again", Toast.LENGTH_SHORT).show()
            }
        })
    }
}