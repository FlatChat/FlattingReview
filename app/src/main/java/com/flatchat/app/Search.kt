package com.flatchat.app

import adapters.SearchAdapter
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_search.*
import models.Flat
import newFlat.NewFlat

/**
 * This is the search classes that receives queries from the user and relays them to firebase, to
 * fetch search results, it uses the Search Adapter class to display the query results.
 * @author Ryan
 */
class Search : AppCompatActivity(), SearchAdapter.OnItemClickListener {

    private lateinit var mSearchText : EditText
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var flatReference: DatabaseReference
    private var flatList: ArrayList<Flat> = ArrayList()
    private lateinit var mAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        flatReference = FirebaseDatabase.getInstance().getReference("flats")

        mSearchText = findViewById(R.id.search_bar_text)
        mRecyclerView = findViewById(R.id.search_view_recycler)

        createViewSearchFlats()

        // Bottom navigation
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.search_screen
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
                    true
                }
                R.id.add_flat_screen -> {
                    val intent = Intent(this, NewFlat::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        mSearchText.addTextChangedListener(object  : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                // Doesn't need to do anything
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Doesn't need to do anything
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = mSearchText.text.toString().trim()
                firebaseSearch(searchText)
            }
        })
    }

    private fun firebaseSearch(searchText : String) {
        mAdapter.clear()
            val query: Query = flatReference.orderByChild("address").startAt(searchText).endAt(searchText + "\uf8ff")
            query.addChildEventListener(object : ChildEventListener{
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    val flat = snapshot.getValue(Flat::class.java)
                    if (flat != null) {
                        flatList.add(flat)
                    }
                    createViewSearchFlats()
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    val flat = snapshot.getValue(Flat::class.java)
                    if (flat != null) {
                        flatList.add(flat)
                    }
                    createViewSearchFlats()
                }

                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val flat = snapshot.getValue(Flat::class.java)
                    if (flat != null) {
                        flatList.add(flat)
                    }
                    createViewSearchFlats()
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    val flat = snapshot.getValue(Flat::class.java)
                    if (flat != null) {
                        flatList.add(flat)
                    }
                    createViewSearchFlats()
                }
            })
    }

    private fun createViewSearchFlats() {
        mAdapter = SearchAdapter(flatList, this)
        search_view_recycler.adapter = mAdapter
        search_view_recycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        search_view_recycler.setHasFixedSize(true)
    }

    /**
     * Implements the click interface so a user can click on a search result and be taken
     * to that flats page.
     *
     * @param item the flat
     */
     override fun onItemClick(item: Flat){
        val intent = Intent(this, FlatScreen::class.java)
        intent.putExtra("flat", item)
        startActivity(intent)
    }
}

