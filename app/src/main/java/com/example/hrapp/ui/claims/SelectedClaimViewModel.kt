package com.example.hrapp.ui.claims



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hrapp.repository.ClaimRepository
import data.Claim


class SelectedClaimViewModel : ViewModel() {
    private val claimRepository: ClaimRepository = ClaimRepository()

    fun retrieve(claimID: String): MutableLiveData<Claim?> {
        return claimRepository.retrieveSelectedClaim(claimID)
    }

}

