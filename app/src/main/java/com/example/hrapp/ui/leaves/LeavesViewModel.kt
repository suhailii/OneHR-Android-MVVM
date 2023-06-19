package com.example.hrapp.ui.leaves

import androidx.lifecycle.ViewModel
import com.example.hrapp.repository.LeavesRepository
import com.example.hrapp.repository.SharedPrefRepository

class LeavesViewModel(private val repo : SharedPrefRepository) : ViewModel() {
    private val leaveRepository: LeavesRepository = LeavesRepository()
    private val eid = repo.getValueString("eid").toString()

    fun addLeaves(startDate: String, endDate: String, remarks: String, type: String){
        leaveRepository.addLeave(eid,startDate,endDate,remarks,type)

    }


}