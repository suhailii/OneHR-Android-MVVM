package com.example.hrapp.ui.leaves

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hrapp.repository.LeavesRepository
import com.example.hrapp.repository.SharedPrefRepository
import data.Leave

class LeavesListViewModel(repo: SharedPrefRepository) : ViewModel() {
    private val leaveRepository: LeavesRepository = LeavesRepository()
    private val eid = repo.getValueString("eid").toString()

    fun retrieve(): MutableLiveData<List<Leave>?>{
        return leaveRepository.retrieveLeaves(eid)
    }
}