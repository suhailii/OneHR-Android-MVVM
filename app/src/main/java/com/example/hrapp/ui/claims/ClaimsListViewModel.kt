package com.example.hrapp.ui.claims

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hrapp.repository.ClaimRepository
import com.example.hrapp.repository.SharedPrefRepository
import data.Claim

class ClaimsListViewModel(private val repo : SharedPrefRepository): ViewModel() {
    private val claimRepository: ClaimRepository = ClaimRepository()
    val eid = repo.getValueString("eid").toString()

    fun retrieve(): MutableLiveData<List<Claim>?> {
        return claimRepository.retrieveClaims(eid)
    }

}

