package com.example.flattingreview

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity

/**
 * Class for presenting the reviews.
 * @author Ryan
 */
class ViewReviews : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reviews)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}
