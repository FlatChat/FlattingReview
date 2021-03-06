package com.flatchat.app

import adapters.ReviewAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_show_all_reviews.*
import models.Review

/**
 * Gets sent a list of reviews and forwards them on into a recycler adapter for them
 * to be displayed.
 * @author Ryan
 */
class ShowAllReviews : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_all_reviews)

        @Suppress("UNCHECKED_CAST")
        val list = intent.getSerializableExtra("list") as ArrayList<Review>

        review_recycler_view.adapter = ReviewAdapter(this, list)
        review_recycler_view.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        review_recycler_view.setHasFixedSize(true)

    }
}