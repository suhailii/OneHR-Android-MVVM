package com.example.hrapp.ui.managerview


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hrapp.repository.LeavesRepository
import data.Leave


class ManagerLeaveSelectViewModel: ViewModel(){
    private val leaveRepository: LeavesRepository = LeavesRepository()

    fun retrieve(leaveID: String): MutableLiveData<Leave?> {
        return leaveRepository.retrieveSelectedLeave(leaveID)
    }

    fun approveLeave(leaveID: String): MutableLiveData<Boolean?> {
        return leaveRepository.approveLeave(leaveID)
    }

    fun rejectLeave(leaveID: String): MutableLiveData<Boolean?> {
        return leaveRepository.rejectLeave(leaveID)
    }

}