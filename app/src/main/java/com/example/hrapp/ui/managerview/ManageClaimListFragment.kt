package com.example.hrapp.ui.managerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hrapp.application.OneHR
import com.example.hrapp.databinding.FragmentManagerclaimlistBinding
import com.example.hrapp.factory.SharedPreferenceVMFactory
import data.Claim

class ManageClaimListFragment : Fragment() {
    private var _binding: FragmentManagerclaimlistBinding? = null
    private lateinit var recyclerview: RecyclerView;
    private lateinit var adapter: ManagerClaimAdapter;
    private val binding get() = _binding!!
    private val managerListVM : ManagerClaimListViewModel by viewModels{
        SharedPreferenceVMFactory((this.requireActivity().application as OneHR).prefRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentManagerclaimlistBinding.inflate(inflater, container, false)
        val root: View = binding.root



        recyclerview = binding.recyclerViewManagerClaims
        recyclerview.layoutManager = LinearLayoutManager(this.context)


        managerListVM.retrieveAll().observe(viewLifecycleOwner, Observer {
            adapter = ManagerClaimAdapter(it as ArrayList<Claim>)
            recyclerview.adapter = adapter
        })



        // Setting the Adapter with the recyclerview

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}