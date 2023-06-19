package com.example.hrapp.ui.managerview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hrapp.repository.ClaimRepository
import com.example.hrapp.repository.SharedPrefRepository
import data.Claim

class ManagerClaimListViewModel(repo: SharedPrefRepository) : ViewModel()  {
    private val claimRepository: ClaimRepository = ClaimRepository()
    private val eid = repo.getValueString("eid").toString()

    fun retrieveAll(): MutableLiveData<List<Claim>?> {
        return claimRepository.retrieveClaimsManager(eid)
    }

}