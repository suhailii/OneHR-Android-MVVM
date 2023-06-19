package com.example.hrapp.ui.jitsi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hrapp.repository.SharedPrefRepository
import com.example.hrapp.repository.UserRepository

class JitsiViewModel(repo: SharedPrefRepository) : ViewModel() {
    private val userRepository: UserRepository = UserRepository()
    private val eid = repo.getValueString("eid").toString()

    /*** Function to retrieve the role of current user ***/
    fun retrieveRole(): MutableLiveData<String?> {
        return userRepository.retrieveRole(eid)
    }

    /*** Function to send notification containing the Room ID ***/
    fun notify(roomID: String){
        userRepository.notifyRoomID(roomID)
    }


}