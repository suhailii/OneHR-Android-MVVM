package com.example.hrapp.ui.leaves

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hrapp.repository.LeavesRepository
import com.example.hrapp.repository.SharedPrefRepository
import data.Leave

class SelectedLeaveViewModel(repo: SharedPrefRepository) : ViewModel() {
    private val leaveRepository: LeavesRepository = LeavesRepository()
    private val eid = repo.getValueString("eid").toString()

    fun retrieve(leaveID: String): MutableLiveData<Leave?> {
        return leaveRepository.retrieveSelectedLeave(leaveID)
    }

    fun edit(leaveID: String, startDate: String, endDate: String, remarks: String, type: String) {
        leaveRepository.editLeave(leaveID, eid, startDate, endDate, remarks, type)
    }

    fun delete(leaveID: String){
        leaveRepository.deleteLeave(leaveID)
    }

}