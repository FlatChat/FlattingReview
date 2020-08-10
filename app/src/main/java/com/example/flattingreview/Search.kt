package com.example.flattingreview

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
                    val intent = Intent(this, CreateFlat::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        mSearchText.addTextChangedListener(object  : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
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
                    val id = snapshot.child("flatID").value as String
                    val address = snapshot.child("address").value as String
                    val beds = snapshot.child("bedrooms").value as String
                    val baths = snapshot.child("bathrooms").value as String
                    val flat = Flat(id, address, beds, baths)
                    flatList.add(flat)
                    createViewSearchFlats()
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    val id = snapshot.child("flatID").value as String
                    val address = snapshot.child("address").value as String
                    val beds = snapshot.child("bedrooms").value as String
                    val baths = snapshot.child("bathrooms").value as String
                    val flat = Flat(id, address, beds, baths)
                    flatList.add(flat)
                    createViewSearchFlats()
                }

                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val id = snapshot.child("flatID").value as String
                    val address = snapshot.child("address").value as String
                    val beds = snapshot.child("bedrooms").value as String
                    val baths = snapshot.child("bathrooms").value as String
                    val flat = Flat(id, address, beds, baths)
                    flatList.add(flat)
                    createViewSearchFlats()
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    val id = snapshot.child("flatID").value as String
                    val address = snapshot.child("address").value as String
                    val beds = snapshot.child("bedrooms").value as String
                    val baths = snapshot.child("bathrooms").value as String
                    val flat = Flat(id, address, beds, baths)
                    flatList.add(flat)
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

     override fun onItemClick(item: Flat, position: Int){
        val intent = Intent(this, FlatScreen::class.java)
        intent.putExtra("flat", item)
        startActivity(intent)
    }
}

