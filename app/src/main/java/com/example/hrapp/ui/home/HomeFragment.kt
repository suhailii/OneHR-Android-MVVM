package com.example.hrapp.ui.home

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hrapp.LocationUtils
import com.example.hrapp.R
import com.example.hrapp.application.OneHR
import com.example.hrapp.databinding.FragmentHomeBinding
import com.example.hrapp.factory.SharedPreferenceVMFactory


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var recyclerview: RecyclerView;
    private lateinit var adapter: RoomIDAdapter;



    private val homeViewModel : HomeViewModel by viewModels{
        SharedPreferenceVMFactory((this.requireActivity().application as OneHR).prefRepository)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        LocationUtils.getInstance(this.requireContext())
        LocationUtils.getLocation()


        homeViewModel.retrieveRoomID().observe(viewLifecycleOwner, Observer {
            recyclerview = binding.rvJitsi
            recyclerview.layoutManager = LinearLayoutManager(this.context)
            adapter = it?.let { it1 -> RoomIDAdapter(it1) }!!
            recyclerview.adapter = adapter

        })


        homeViewModel.retrieveRole().observe(viewLifecycleOwner, Observer {
            if (!it.equals("manager")){
                binding.linearLayoutManageLeave.visibility = GONE
                binding.linearLayoutManageClaim.visibility = GONE
            }
        })

        homeViewModel.retrieveAttendance().observe(viewLifecycleOwner, Observer {
            Log.d("inout", it.inout.toString())
            Log.d("location", it.location.toString())
            Log.d("time", it.time.toString())

            if (!it.inout.toString().toBoolean()){
                binding.textViewAttendance.text = "Checked Out: "+it.time.toString()
                binding.imageView3.setImageResource(R.drawable.cross)
            }
            else{
                binding.textViewAttendance.text = "Checked In: "+it.time.toString()
                binding.imageView3.setImageResource(R.drawable.greentick)
            }
            binding.textViewLocation.text = it.location.toString()
            binding.textViewInOut.text = it.inout.toString()

        })

        homeViewModel.retrieveLatestLeave().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.tvLeaveType.text = it.type.toString()
                binding.tvStartDate.text = "From: " + it.startDate.toString()
                binding.tvEndDate.text = "Until: " + it.endDate.toString()
                binding.tvStatus.text = it.status.toString()

            }
        })

        homeViewModel.retrieveLatestClaim().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.tvClaimType.text = it.type.toString()
                binding.tvAmount.text = "- $" + it.amount.toString()
                binding.tvRemarks.text = it.remarks.toString()
                if (it.status.toString() == "NOTIFIEDA"){
                    binding.tvStatusClaims.text = "Approved"
                }else if (it.status.toString() == "NOTIFIEDR"){
                    binding.tvStatusClaims.text = "Rejected"
                }else{
                    binding.tvStatusClaims.text = it.status.toString()
                }
            }
        })




        homeViewModel.retrieveLatestClaimManager().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.tvClaimName.text = it.name.toString()
                binding.tvManageType.text = it.type.toString()
                binding.tvManageRemarks.text = it.remarks.toString()
                binding.tvManageAmount.text = "- $" + it.amount.toString()
            }
        })

        homeViewModel.retrieveLatestLeaveManager().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.tvLeaveName.text = it.name.toString()
                binding.tvManageTypeLeave.text = it.type.toString()
                binding.tvManageStart.text = "From: " + it.startDate.toString()
                binding.tvManageEnd.text = "Until: " + it.endDate.toString()
            }
        })


        binding.linearLayoutAttendance.setOnClickListener {
            val inout = binding.textViewInOut.text
            if (inout.toString() == "false") {
                LocationUtils.location.value?.let { location -> compareWorkplace(location, true) }
            }else{
                LocationUtils.location.value?.let { location -> compareWorkplace(location,false) }
            }
        }

        binding.linearClaim.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_navigation_home_to_claimsListFragment)
        }

        binding.linearLeave.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_navigation_home_to_leavesListFragment)
        }


        homeViewModel.curLocation.observe(viewLifecycleOwner, Observer { location : String? ->
            Log.d("HomeFragment : ", "CurLocation : $location")
            if (location == "Invalid Location"){
                Toast.makeText(this.requireContext(), "Invalid Location. Please check in/out only in workplaces !"
                    , Toast.LENGTH_LONG).show()
            }
        })

        binding.imageButtonLeaves.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_navigation_home_to_leavesListFragment)
        }
        binding.imageButtonClaims.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_navigation_home_to_claimsListFragment)
        }
        binding.imageButtonManageClaim.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_navigation_home_to_manageClaimListFragment)
        }
        binding.imageButtonManageLeave.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_navigation_home_to_manageLeaveListFragment)
        }




        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun compareWorkplace(loc: Location, bool : Boolean){
        val lat = loc.latitude.toString()
        val lon = loc.longitude.toString()
        Log.d("HomeFragment : ", "Latitude: $lat")
        Log.d("HomeFragment : ", "Longtitude : $lon")
        homeViewModel.compareWorkplace(loc.latitude.toString(), loc.longitude.toString(), bool)
    }
}