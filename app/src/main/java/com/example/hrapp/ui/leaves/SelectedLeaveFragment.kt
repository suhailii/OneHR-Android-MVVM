package com.example.hrapp.ui.leaves

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.hrapp.R
import com.example.hrapp.databinding.FragmentSelectedleaveBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import androidx.lifecycle.Observer
import com.example.hrapp.application.OneHR
import com.example.hrapp.factory.SharedPreferenceVMFactory
import java.text.SimpleDateFormat
import java.util.*

class SelectedLeaveFragment: Fragment()  {
    private var _binding: FragmentSelectedleaveBinding? = null
    private val binding get() = _binding!!
    private val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    //retrieving id from prev nav
    private val args: SelectedLeaveFragmentArgs by navArgs()
    private val selectedVM : SelectedLeaveViewModel by viewModels{
        SharedPreferenceVMFactory((this.requireActivity().application as OneHR).prefRepository)
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectedleaveBinding.inflate(inflater, container, false)
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

        //retrieve id from arg
        val id = args.id
        //get leave details from db and set to fields
        selectedVM.retrieve(id).observe(viewLifecycleOwner, Observer{
            if (it != null) {
                binding.spinnerLeave.setSelection((binding.spinnerLeave.adapter as ArrayAdapter<String?>).getPosition(it.type))
                binding.dateStartRangeField.setText(it.startDate)
                binding.dateEndRangeField.setText(it.endDate)
                binding.etRemarks.setText(it.remarks)
            }
        })



        binding.btnEdit.setOnClickListener{
//            TODO: edit leave in Firebase
            val startDate = binding.dateStartRangeField.text.toString()
            val endDate = binding.dateEndRangeField.text.toString()
            val remarks = binding.etRemarks.text.toString()
            val type = binding.spinnerLeave.selectedItem.toString()

            selectedVM.edit(id,startDate,endDate,remarks,type)
            //TODO: bring to homepage or leavelistpage
            Navigation.findNavController(it).navigate(R.id.action_selectedLeaveFragment_to_leavesListFragment)
        }

        binding.btnDelete.setOnClickListener {
            selectedVM.delete(id)
            Navigation.findNavController(it).navigate(R.id.action_selectedLeaveFragment_to_leavesListFragment)
        }


        return root
    }
}