package newFlat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.flatchat.app.R
import newFlat.NewFlat.Flat.bathrooms

class Details : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(
            R.layout.fragment_details,
            container,
            false
        )

        val backButton = view.findViewById<Button>(R.id.button_back)
        val nextButton = view.findViewById<Button>(R.id.button_next)
        val bedrooms = view.findViewById<EditText>(R.id.bedrooms)
        val bathrooms = view.findViewById<EditText>(R.id.bathrooms)

        bedrooms.background = ResourcesCompat.getDrawable(resources, R.drawable.rounded_edit_text, null)
        bathrooms.background = ResourcesCompat.getDrawable(resources, R.drawable.rounded_edit_text, null)

        backButton?.setOnClickListener{
            (activity as NewFlat?)!!.setFragment(0)
        }

        nextButton?.setOnClickListener{
            checkCollectPass(bedrooms, bathrooms)
        }

        return view
    }

    private fun checkCollectPass(bedrooms: EditText, bathrooms: EditText){
        if(bedrooms.text.toString() == "" && bathrooms.text.toString() == ""){
            bedrooms.background = ResourcesCompat.getDrawable(resources, R.drawable.button_red_outline, null)
            bathrooms.background = ResourcesCompat.getDrawable(resources, R.drawable.button_red_outline, null)
        } else if(bathrooms.text.toString() == "") {
            bathrooms.background = ResourcesCompat.getDrawable(resources, R.drawable.button_red_outline, null)
        } else if(bedrooms.text.toString() == "") {
            bedrooms.background = ResourcesCompat.getDrawable(resources, R.drawable.button_red_outline, null)
        } else {
            NewFlat.bedrooms = bedrooms.text.toString()
            NewFlat.bathrooms = bathrooms.text.toString()
            (activity as NewFlat?)!!.setFragment(2)
        }
    }

}