package onboarding

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flatchat.app.HomeScreen
import com.flatchat.app.R
import com.flatchat.app.Search
import com.flatchat.app.Settings
import com.google.android.material.bottomnavigation.BottomNavigationView


class OnBoarding : AppCompatActivity() {

    private val firstScreen = Address()
    private val secondScreen = Details()
    private val thirdScreen = Image()
    private val forthScreen = Confirm()
    companion object Flat {
        var address = ""
        var bathrooms = ""
        var bedrooms = ""
        var imageBitmap: Bitmap? = null
        var imageURI: Uri? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        val position = 0

        setFragment(position)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.add_flat_screen
        bottomNavigation.setOnNavigationItemSelectedListener {
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
                    val intent = Intent(this, Search::class.java)
                    startActivity(intent)
                    true
                }
                R.id.add_flat_screen -> {
                    true
                }
                else -> false
            }
        }
    }

    fun setFragment(position: Int){
        when (position) {
            0 -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view,
                firstScreen).commit()
            1 -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view,
                secondScreen).commit()
            2 -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view,
                thirdScreen).commit()
            3 -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view,
                forthScreen).commit()
        }
    }

//    private fun writeNewFlat(){
//        val myRef = FirebaseDatabase.getInstance().getReference("flats")
//        val flatID = myRef.push().key
//        val imageID = "image$flatID"
//        val storageRef = Firebase.storage.reference.child("flats/$imageID.jpg")
////        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
//        val baos = ByteArrayOutputStream()
//        imageBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//        val data = baos.toByteArray()
//
//        val flat = Flat(
//                flatID,
//                address,
//                bedrooms,
//                bathrooms,
//                0
//        )
//        myRef.child(flatID.toString()).setValue(flat)
//
//        val uploadTask = storageRef.putBytes(data)
//        val intent = Intent(this, HomeScreen::class.java)
//
//        uploadTask.addOnFailureListener {
//            Log.d("File Upload", "Failure")
//        }.addOnSuccessListener {
//            Log.d("File Upload", "Successful")
//        }
//        startActivity(intent)
//    }
}