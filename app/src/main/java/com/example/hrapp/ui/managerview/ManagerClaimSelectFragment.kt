package com.example.hrapp.ui.managerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.hrapp.R
import com.example.hrapp.databinding.FragmentManagerclaimselectBinding
import java.util.*

class ManagerClaimSelectFragment : Fragment(){
    private lateinit var managerSelectVM: ManagerClaimSelectViewModel
    private var _binding: FragmentManagerclaimselectBinding? = null
    private val binding get() = _binding!!

    //retrieving id from prev nav
    val args: ManagerClaimSelectFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentManagerclaimselectBinding.inflate(inflater, container, false)
        val root: View = binding.root
        managerSelectVM = ViewModelProvider(this).get(ManagerClaimSelectViewModel::class.java)

        //retrieve id from arg
        val id = args.id
        //get leave details from db and set to fields
        managerSelectVM.retrieve(id).observe(viewLifecycleOwner, Observer { claim ->
            if (claim != null) {
                binding.textViewClaimsType.setText(claim.type)
                binding.textViewClaimsAmount.setText(claim.amount)
                binding.textViewremarks.setText(claim.remarks)
                val image = claim.image
                if (image != null) {
                    managerSelectVM.retrieveStorageImage(image).observe(viewLifecycleOwner, Observer {
                        binding.imagePreview.setImageBitmap(it)
                        binding.imagePreview.getLayoutParams().height = 500
                        binding.imagePreview.getLayoutParams().width = 500
                    })

                }
            }
        })

        binding.btnAccept.setOnClickListener {
            managerSelectVM.approveClaim(id).observe(viewLifecycleOwner, Observer { sucess ->
                if (sucess == true){
                    Navigation.findNavController(it).navigate(R.id.action_managerClaimSelectFragment_to_manageClaimListFragment)
                }
            })
        }

        binding.btnReject.setOnClickListener {
            managerSelectVM.rejectClaim(id).observe(viewLifecycleOwner, Observer { sucess ->
                if (sucess == true){
                    Navigation.findNavController(it).navigate(R.id.action_managerClaimSelectFragment_to_manageClaimListFragment)
                }
            })
        }
        return root

    }
}