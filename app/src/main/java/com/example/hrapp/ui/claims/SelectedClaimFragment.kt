package com.example.hrapp.ui.claims

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.lifecycle.Observer
import com.example.hrapp.databinding.FragmentSelectedclaimBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.File


class SelectedClaimFragment: Fragment() {
    private lateinit var selectedCLaim: SelectedClaimViewModel
    private var _binding: FragmentSelectedclaimBinding? = null
    private val binding get() = _binding!!

    //retrieving id from prev nav
    val args: SelectedClaimFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectedclaimBinding.inflate(inflater, container, false)
        val root: View = binding.root
        selectedCLaim = ViewModelProvider(this).get(SelectedClaimViewModel::class.java)

        //retrieve id from arg
        val id = args.id
        //get leave details from db and set to fields
        selectedCLaim.retrieve(id).observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.textViewClaimsType.setText(it.type)
                binding.textViewClaimsAmount.setText(it.amount)
                binding.textViewremarks.setText(it.remarks)
                val image=it.image
                val storageRef= FirebaseStorage.getInstance("gs://hrapp-764e0.appspot.com").reference.child("uploads/$image")

                val localfile= File.createTempFile("tempImage","jpg")
                storageRef.getFile(localfile).addOnSuccessListener{
                    val bitmap=BitmapFactory.decodeFile(localfile.absolutePath)
                    binding.imagePreview.setImageBitmap(bitmap)
                    binding.imagePreview.getLayoutParams().height = 500
                    binding.imagePreview.getLayoutParams().width = 500
                }.addOnFailureListener{
                    Toast.makeText(this.context, "Error retrieving from db DB", Toast.LENGTH_LONG).show()
                }
            }
        })

        return root
    }

}
