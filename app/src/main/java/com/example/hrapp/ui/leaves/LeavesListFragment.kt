package com.example.hrapp.ui.leaves

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
import com.example.hrapp.databinding.FragmentLeaveslistBinding
import com.example.hrapp.factory.SharedPreferenceVMFactory
import data.Leave

class LeavesListFragment: Fragment() {
    private var _binding: FragmentLeaveslistBinding? = null
    private lateinit var recyclerview: RecyclerView;
    private lateinit var adapter: LeavesAdapter;

    private val leavesListViewModel : LeavesListViewModel by viewModels{
        SharedPreferenceVMFactory((this.requireActivity().application as OneHR).prefRepository)
    }
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        _binding = FragmentLeaveslistBinding.inflate(inflater, container, false)
        val root: View = binding.root




        recyclerview = binding.recyclerViewLeaves
        recyclerview.layoutManager = LinearLayoutManager(this.context)


        leavesListViewModel.retrieve().observe(viewLifecycleOwner, Observer {
            adapter = LeavesAdapter(it as ArrayList<Leave>)
            recyclerview.adapter = adapter

        })




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}