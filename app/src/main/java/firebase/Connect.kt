package firebase

import adapters.ReviewAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flattingreview.Account
import com.example.flattingreview.HomeScreen
import com.example.flattingreview.Settings
import com.example.flattingreview.WriteReview
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_view_reviews.*
import models.Flat
import models.Review
import kotlin.math.round

class Connect {

    var flats: ArrayList<Flat> = ArrayList()
    var reviewList: ArrayList<Review> = ArrayList()
    var ratingList: HashMap<String, ArrayList<Double>> = HashMap()
    var numberOfReviews: HashMap<String, Int> = HashMap()
    private val flatReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("flats")

    /**
     * On start it will connect to the database under the reference reviews and flats. And collect all
     * the data for both the flats and reviews to display in the recycler views in the home screen.
     */
    fun getAllFlats(): ArrayList<Flat> {
        val flatListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Log.w("ViewReview", "loadItem:onCancelled")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val id = ds.child("flatID").value as String
                    val address = ds.child("address").value as String
                    val beds = ds.child("bedrooms").value as String
                    val baths = ds.child("bathrooms").value as String
                    val flat = Flat(id, address, beds, baths)
                    flats.add(flat)
                }
            }
        }
        flatReference.orderByKey().addValueEventListener(flatListener)
        return flats
    }

    fun getAllReview() {
        val reviewReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("reviews")
        val reviewListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Log.w("ViewReview", "loadItem:onCancelled")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val reviewID = ds.child("reviewID").value as String
                    val userID = ds.child("userID").value as String
                    val flatID = ds.child("flatID").value as String
                    val name = ds.child("name").value as String
                    val clean = ds.child("cleanliness").value as Double
                    val lord = ds.child("landlord").value as Double
                    val location = ds.child("location").value as Double
                    val value = ds.child("value").value as Double
                    val anon = ds.child("anonymous").value as Boolean
                    val date = ds.child("date").value as String
                    val comment = ds.child("comment").value as String

                    val rev = Review(
                        reviewID,
                        userID,
                        flatID,
                        name,
                        clean - 0.1,
                        lord - 0.1,
                        location - 0.1,
                        value - 0.1,
                        anon,
                        date,
                        comment
                    )
                    val ratings: ArrayList<Double> = ArrayList()
                    ratings.add(round((clean + lord + location + value - 0.4) / 4))
                    ratingList[flatID] = ratings
                    numberOfReviews[flatID] = +1
                    if (comment != "") {
                        reviewList.add(rev)
                    }
                }
            }
        }
        reviewReference.orderByKey().addValueEventListener(reviewListener)
    }

    fun getReviewByFlat(flat: Flat) {
        reviewList.removeAll(reviewList)
        val reviewReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("reviews")
        val reviewListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Log.w("ViewReview", "loadItem:onCancelled")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    if (flat.flatID == ds.child("flatID").value as String) {
                        val reviewID = ds.child("reviewID").value as String
                        val userID = ds.child("userID").value as String
                        val flatID = ds.child("flatID").value as String
                        val name = ds.child("name").value as String
                        val clean = ds.child("cleanliness").value as Double
                        val lord = ds.child("landlord").value as Double
                        val location = ds.child("location").value as Double
                        val value = ds.child("value").value as Double
                        val anon = ds.child("anonymous").value as Boolean
                        val date = ds.child("date").value as String
                        val comment = ds.child("comment").value as String

                        val rev = Review(
                            reviewID,
                            userID,
                            flatID,
                            name,
                            clean - 0.1,
                            lord - 0.1,
                            location - 0.1,
                            value - 0.1,
                            anon,
                            date,
                            comment
                        )
                        if (comment != "") {
                            reviewList.add(rev)
                        }
                    }
                }

            }
        }
        reviewReference.orderByKey().addValueEventListener(reviewListener)
    }
}