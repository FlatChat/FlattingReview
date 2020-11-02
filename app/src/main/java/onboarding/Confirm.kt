package onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.flatchat.app.R

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

        val bedrooms = view.findViewById<EditText>(R.id.bedrooms_text)
        val bathrooms = view.findViewById<EditText>(R.id.bathrooms_text)
        val address = view.findViewById<EditText>(R.id.address_text)
        val image = view.findViewById<ImageView>(R.id.image_check)

        bedrooms.setText(OnBoarding.bedrooms)
        bathrooms.setText(OnBoarding.bathrooms)
        address.setText(OnBoarding.address)
        image.setImageURI(OnBoarding.imageURI)


        return view
    }
}