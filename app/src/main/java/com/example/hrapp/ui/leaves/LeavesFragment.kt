package com.example.hrapp.ui.leaves

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.hrapp.R
import com.example.hrapp.application.OneHR
import com.example.hrapp.databinding.FragmentLeavesBinding
import com.example.hrapp.factory.SharedPreferenceVMFactory
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*


class LeavesFragment : Fragment() {
    private var _binding: FragmentLeavesBinding? = null
    private val binding get() = _binding!!
    private val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val leaveVM : LeavesViewModel by viewModels{
        SharedPreferenceVMFactory((this.requireActivity().application as OneHR).prefRepository)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentLeavesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val spinner = binding.spinnerLeave
        this.context?.let {
            ArrayAdapter.createFromResource(it, R.array.leavesType, android.R.layout.simple_spinner_dropdown_item)
                .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter }
        }

        binding.dateStartRangeField.setOnClickListener {
            val constraints = CalendarConstraints.Builder()
                .setStart(MaterialDatePicker.thisMonthInUtcMilliseconds())
                .setValidator(DateValidatorPointForward.now())
                .build()
            val datePickerBuilder: MaterialDatePicker.Builder<Pair<Long, Long>> = MaterialDatePicker
                .Builder
                .dateRangePicker()
                .setTitleText("Select a date")
                .setCalendarConstraints(constraints)
            val datePicker = datePickerBuilder.build()
            datePicker.show(this.parentFragmentManager, "DATE_PICKER_RANGE")

            datePicker.addOnPositiveButtonClickListener {
                val startDate = sdf.format(it.first)
                val endDate = sdf.format(it.second)
                binding.dateStartRangeField.setText(startDate)
                binding.dateEndRangeField.setText(endDate)
            }
        }


        binding.btnSubmitLeave.setOnClickListener{
            if (binding.dateStartRangeField.text?.isEmpty() == true){
                Toast.makeText(this.requireContext(), "Please enter date!", Toast.LENGTH_LONG).show()
            }else{
                submitLeave()
                Navigation.findNavController(it).navigate(R.id.action_navigation_leave_to_leavesListFragment)
            }
        }
        return root
    }

//    fun popupLeaveInfo(){
//        //TODO: call viewModel to retrieve data from firebase/function
//    }

    private fun submitLeave(){
        //TODO: validate()
        val startDate = binding.dateStartRangeField.text.toString()
        val endDate = binding.dateEndRangeField.text.toString()
        val remarks = binding.etRemarks.text.toString()
        val type = binding.spinnerLeave.selectedItem.toString()
        leaveVM.addLeaves(startDate,endDate,remarks,type)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}