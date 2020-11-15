package newFlat

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.flatchat.app.HomeScreen
import com.flatchat.app.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import models.Flat
import java.io.ByteArrayOutputStream


class Confirm : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(
            R.layout.fragment_confirm,
            container,
            false
        )

        val address = view.findViewById<TextView>(R.id.flat_address)
        val image = view.findViewById<ImageView>(R.id.flat_image)
        val finishButton = view.findViewById<Button>(R.id.finish_button)
        val backButton = view.findViewById<Button>(R.id.button_back)
        val terms = view.findViewById<CheckBox>(R.id.terms_conditions_box)

        backButton?.setOnClickListener{
            (activity as NewFlat?)!!.setFragment(2)
        }

        finishButton?.setOnClickListener{
            if (terms.isChecked){
                writeNewFlat()
            } else {
                Toast.makeText(activity, "Agree to the terms and conditions",
                        Toast.LENGTH_SHORT).show()
            }
        }

        address.text = NewFlat.address.split(",")[0]
        image.setImageURI(NewFlat.imageURI)

        return view
    }

    private fun writeNewFlat(){
        val myRef = FirebaseDatabase.getInstance().getReference("flats")
        val flatID = myRef.push().key
        val imageID = "image$flatID"
        val storageRef = Firebase.storage.reference.child("flats/$imageID.jpg")
        val baos = ByteArrayOutputStream()
        NewFlat.imageBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val flat = Flat(
            flatID,
            NewFlat.address,
            NewFlat.bedrooms,
            NewFlat.bathrooms,
            0
        )
        myRef.child(flatID.toString()).setValue(flat)
        val uploadTask = storageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Log.d("File Upload", "Failure")
        }.addOnSuccessListener {
            Log.d("File Upload", "Successful")
        }
        val intent = Intent(activity, HomeScreen::class.java)
        startActivity(intent)
    }
}