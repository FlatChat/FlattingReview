package newFlat

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.flatchat.app.R

class Image : Fragment() {

    private val requestImageCapture = 1
    private lateinit var imageFlat: ImageView
    companion object {
        //image pick code
        private const val IMAGE_PICK_CODE = 1000
        //Permission code
        private const val PERMISSION_CODE = 1001
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(
            R.layout.fragment_image,
            container,

            false
        )

        val backButton = view.findViewById<Button>(R.id.button_back)
        val nextButton = view.findViewById<Button>(R.id.button_next)
        imageFlat = view.findViewById(R.id.flat_image_upload)
        val uploadImage = view.findViewById<Button>(R.id.upload_image)
        val camera = view.findViewById<Button>(R.id.camera_button)

        imageFlat.setImageResource(R.drawable.image)

        backButton?.setOnClickListener{
            (activity as NewFlat?)!!.setFragment(1)
        }

        nextButton?.setOnClickListener{
            (activity as NewFlat?)!!.setFragment(3)
        }

        //BUTTON CLICK
        uploadImage.setOnClickListener {
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE)
                }
                else{
                    //permission already granted
                    pickImageFromGallery()
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery()
            }
        }

        camera.setOnClickListener {
            dispatchTakePictureIntent()
        }

        return view
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    /**
     * Handles the request permissions.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(activity, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * Handles the results of the picked image.
     *
     * @param requestCode
     * @param resultCode
     * @param data The image
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            if (data != null) {
                imageFlat.setImageURI(data.data)
                NewFlat.imageURI = data.data
            } else {
                Toast.makeText(activity, "Error Uploading Photo", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == requestImageCapture && resultCode == AppCompatActivity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageFlat.setImageBitmap(imageBitmap)
            NewFlat.imageBitmap = imageBitmap
        } else {
            Toast.makeText(activity, "Error Uploading Photo", Toast.LENGTH_SHORT).show()
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity?.packageManager!!)?.also {
                startActivityForResult(takePictureIntent, requestImageCapture)
            }
        }
    }

}