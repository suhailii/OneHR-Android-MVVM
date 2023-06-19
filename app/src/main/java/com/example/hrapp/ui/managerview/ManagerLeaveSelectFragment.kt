package com.example.hrapp.ui.managerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.hrapp.R
import com.example.hrapp.databinding.FragmentManagerleaveselectBinding

class ManagerLeaveSelectFragment: Fragment(){
    private lateinit var managerSelectVM: ManagerLeaveSelectViewModel
    private var _binding: FragmentManagerleaveselectBinding? = null
    private val binding get() = _binding!!

    //retrieving id from prev nav
    val args: ManagerLeaveSelectFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentManagerleaveselectBinding.inflate(inflater, container, false)
        val root: View = binding.root
        managerSelectVM = ViewModelProvider(this).get(ManagerLeaveSelectViewModel::class.java)

        //retrieve id from arg
        val id = args.id
        //get leave details from db and set to fields
        managerSelectVM.retrieve(id).observe(viewLifecycleOwner, Observer { leave ->
            if (leave != null) {
                binding.textViewLeaveType.setText(leave.type)
                binding.textViewStartDate.setText(leave.startDate)
                binding.textViewEndDate.setText(leave.endDate)
                binding.textViewRemarksLeave.setText(leave.remarks)
            }
        })

        binding.btnAcceptLeave.setOnClickListener {
            managerSelectVM.approveLeave(id).observe(viewLifecycleOwner, Observer { sucess ->
                if (sucess == true){
                    Navigation.findNavController(it).navigate(R.id.action_managerLeaveSelectFragment_to_manageLeaveListFragment)
                }
            })
        }

        binding.btnRejectLeave.setOnClickListener {
            managerSelectVM.rejectLeave(id).observe(viewLifecycleOwner, Observer { sucess ->
                if (sucess == true){
                    Navigation.findNavController(it).navigate(R.id.action_managerLeaveSelectFragment_to_manageLeaveListFragment)
                }
            })
        }
        return root

    }
}