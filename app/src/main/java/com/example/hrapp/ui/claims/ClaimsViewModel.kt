package com.example.hrapp.ui.claims


import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.hrapp.repository.ClaimRepository
import com.example.hrapp.repository.SharedPrefRepository

class ClaimsViewModel(private val repo : SharedPrefRepository) : ViewModel() {
    private val claimRepository: ClaimRepository = ClaimRepository()
    val eid = repo.getValueString("eid").toString()


    fun uploadClaim(filePath: Uri?, amount: String, remarks: String, type: String){
        claimRepository.uploadImage(filePath,eid,amount,remarks,type)
    }

}
