package com.example.flattingreview

import adapters.PopularFlatAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.android.synthetic.main.activity_show_all_flats.*
import models.Flat

class ShowAllFlats : AppCompatActivity(),  PopularFlatAdapter.OnItemClickListener{

    private var ratingList: HashMap<String, ArrayList<Double>> = HashMap()
    private var numberOfReviews: HashMap<String, Int> = HashMap()
    private var layout = "flat_layout_fill_width"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_all_flats)

        val list = intent.getSerializableExtra("list") as ArrayList<Flat>
        val ratingList = intent.getSerializableExtra("ratingList") as HashMap<String, ArrayList<Double>>

        show_all_flats.adapter = PopularFlatAdapter(this, list, ratingList, this, layout)
        show_all_flats.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        show_all_flats.setHasFixedSize(true)

    }

    override fun onItemClick(item: Flat, position: Int) {
        val intent = Intent(this, FlatScreen::class.java)
        intent.putExtra("flat", item)
        val array = ratingList[item.flatID]
        var sum = 0.0
        if(!array.isNullOrEmpty()){
            for(i in array) {
                sum += i
            }
        }
        if (array != null) {
            intent.putExtra("overallRating", (sum / array.size).toString())
        } else {
            intent.putExtra("overallRating", "0")
        }
        intent.putExtra("numberOfRatings", numberOfReviews[item.flatID])
        startActivity(intent)
    }
}