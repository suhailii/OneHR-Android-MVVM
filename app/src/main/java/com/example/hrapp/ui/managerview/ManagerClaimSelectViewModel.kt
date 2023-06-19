package com.example.hrapp.ui.managerview

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hrapp.repository.ClaimRepository
import data.Claim

class ManagerClaimSelectViewModel : ViewModel(){
    private val claimRepository: ClaimRepository = ClaimRepository()

    fun retrieve(claimID: String): MutableLiveData<Claim?> {
        return claimRepository.retrieveSelectedClaim(claimID)
    }
    fun retrieveStorageImage(image : String): MutableLiveData<Bitmap?> {
        return claimRepository.retrieveStorageImage(image)
    }
    fun approveClaim(claimID: String):MutableLiveData<Boolean?>{
        return claimRepository.approveClaim(claimID)
    }

    fun rejectClaim(claimID: String):MutableLiveData<Boolean?>{
        return claimRepository.rejectClaim(claimID)
    }

}