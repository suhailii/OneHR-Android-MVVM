package com.example.hrapp.ui.claims

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
import com.example.hrapp.databinding.FragmentClaimsinfoBinding
import com.example.hrapp.factory.SharedPreferenceVMFactory
import data.Claim

class ClaimsListFragment: Fragment() {
    private var _binding: FragmentClaimsinfoBinding? = null
    private lateinit var recyclerview: RecyclerView;
    private lateinit var adapter: ClaimsAdapter;

    private val claimsListViewModel : ClaimsListViewModel by viewModels{
        SharedPreferenceVMFactory((this.requireActivity().application as OneHR).prefRepository)
    }



    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {





        _binding = FragmentClaimsinfoBinding.inflate(inflater, container, false)
        val root: View = binding.root


        recyclerview = binding.recyclerViewClaims
        recyclerview.layoutManager = LinearLayoutManager(this.context)


        claimsListViewModel.retrieve().observe(viewLifecycleOwner, Observer {
            adapter = ClaimsAdapter(it as ArrayList<Claim>)
            recyclerview.adapter = adapter

        })



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}