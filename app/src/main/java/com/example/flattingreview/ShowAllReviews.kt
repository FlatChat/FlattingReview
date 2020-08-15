package com.example.flattingreview

import adapters.PopularFlatAdapter
import adapters.ReviewAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_show_all_flats.*
import kotlinx.android.synthetic.main.activity_show_all_reviews.*
import models.Flat
import models.Review

class ShowAllReviews : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_all_reviews)

        val list = intent.getSerializableExtra("list") as ArrayList<Review>

        review_recycler_view.adapter = ReviewAdapter(list)
        review_recycler_view.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        review_recycler_view.setHasFixedSize(true)

    }
}