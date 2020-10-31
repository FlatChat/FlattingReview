package onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.flatchat.app.HomeScreen
import com.flatchat.app.R

class Image : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(
            R.layout.fragment_third_screen,
            container,
            false
        )

        val backButton = view.findViewById<Button>(R.id.button_back)
        val nextButton = view.findViewById<Button>(R.id.button_next)

        backButton?.setOnClickListener{
            (activity as OnBoarding?)!!.setFragment(1)
        }

        nextButton?.setOnClickListener{
            val intent = Intent(activity, HomeScreen::class.java)
            startActivity(intent)
        }

        return view
    }

}