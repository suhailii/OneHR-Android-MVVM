package com.example.hrapp.ui.claims

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.hrapp.R
import com.example.hrapp.application.OneHR
import com.example.hrapp.databinding.FragmentClaimsBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.io.ByteArrayOutputStream
import java.io.File

class ClaimsFragment : Fragment() {
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private lateinit var claimsViewModel: ClaimsViewModel
    private var _binding: FragmentClaimsBinding? = null
    private val CAMERA_REQUEST_CODE = 1


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        claimsViewModel = ClaimsViewModel((this.requireActivity().application as OneHR).prefRepository)


        _binding = FragmentClaimsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val spinnerClaims: Spinner = binding.spinnerClaims
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.claimstype,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerClaims.adapter = adapter
        }


        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        binding.btnChooseImage.setOnClickListener { launchGallery() }
        binding.buttonTakeImage.setOnClickListener{CaptureImage()}
        binding.btnUploadImage.setOnClickListener {
            val amount = binding.editAmount.text.toString()
            val remarks = binding.editRemarks.text.toString()
            val type = binding.spinnerClaims.selectedItem.toString()
            if (amount.isNotEmpty()){
                claimsViewModel.uploadClaim(filePath,amount,remarks,type)
                Navigation.findNavController(it).navigate(R.id.action_navigation_claims_to_claimsListFragment)
            }else{
                Toast.makeText(this.requireContext(), "Please enter amount!", Toast.LENGTH_LONG).show()
            }

        }
        binding.buttonTakeImage.setOnClickListener{
            val appPerms = arrayOf(
                Manifest.permission.CAMERA
            )
            activityResultLauncher.launch(appPerms)
        }




        return root
    }
    private var activityResultLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) { result ->
            var allAreGranted = true
            for(b in result.values) {
                allAreGranted = allAreGranted && b
            }
            if(allAreGranted) {
                CaptureImage()
            }else{
            }
        }



    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    private fun CaptureImage(){
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA_REQUEST_CODE)


        ///startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    // Function to check and request permission.


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }

            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, filePath)
                binding.imagePreview.visibility = View.VISIBLE
                binding.imagePreview.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if(requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val bitmap = data?.extras?.get("data") as Bitmap
            binding.imagePreview.visibility = View.VISIBLE
            binding.imagePreview.setImageBitmap(bitmap)
            binding.imagePreview.layoutParams.height = 720
            binding.imagePreview.layoutParams.width = 1080
            val file = File(this.context?.cacheDir,"CUSTOM NAME") //Get Access to a local file.
            file.delete() // Delete the File, just in Case, that there was still another File
            file.createNewFile()
            val fileOutputStream = file.outputStream()
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream)
            val bytearray = byteArrayOutputStream.toByteArray()
            fileOutputStream.write(bytearray)
            fileOutputStream.flush()
            fileOutputStream.close()
            byteArrayOutputStream.close()
            filePath = file.toUri()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}










