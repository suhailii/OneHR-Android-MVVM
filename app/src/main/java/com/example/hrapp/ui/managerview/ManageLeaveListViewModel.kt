package com.example.hrapp.ui.managerview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hrapp.repository.LeavesRepository
import com.example.hrapp.repository.SharedPrefRepository
import data.Leave

class ManageLeaveListViewModel(repo: SharedPrefRepository) : ViewModel()  {
    private val leaveRepository: LeavesRepository = LeavesRepository()
    private val eid = repo.getValueString("eid").toString()

    fun retrieveAll(): MutableLiveData<List<Leave>?> {
        return leaveRepository.retrieveLeavesManager(eid)
    }

}