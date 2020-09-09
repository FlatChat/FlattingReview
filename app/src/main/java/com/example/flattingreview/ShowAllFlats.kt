package com.example.flattingreview

import adapters.PopularFlatAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_show_all_flats.*
import models.Flat

/**
 * Gets sent a list of flats and forwards them on into a recycler adapter for them
 * to be displayed.
 * @author Ryan
 */
class ShowAllFlats : AppCompatActivity(),  PopularFlatAdapter.OnItemClickListener{

    private var layout = "flat_layout_fill_width"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_all_flats)

        @Suppress("UNCHECKED_CAST")
        val list = intent.getSerializableExtra("list") as ArrayList<Flat>
        @Suppress("UNCHECKED_CAST")
        val ratingList =
            intent.getSerializableExtra("ratingList") as HashMap<String, Double>

        show_all_flats.adapter =
            PopularFlatAdapter(this, list, ratingList, this, layout)
        show_all_flats.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        show_all_flats.setHasFixedSize(true)

    }

    /**
     * Receives the flat the user has clicked on in the recycler view and opens the
     * flat screen activity, it puts the flat and the rating/review details into the intent
     * for the other activity.
     *
     * @param item the flat that the user has clicked on
     */
    override fun onItemClick(item: Flat) {
        val intent = Intent(this, FlatScreen::class.java)
        intent.putExtra("flat", item)
        startActivity(intent)
    }
}