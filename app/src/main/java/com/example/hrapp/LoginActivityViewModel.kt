package com.example.hrapp

import androidx.lifecycle.*
import com.example.hrapp.repository.SharedPrefRepository
import com.example.hrapp.repository.UserRepository


class LoginActivityViewModel(private val repo : SharedPrefRepository) : ViewModel() {
    private val userRepository: UserRepository = UserRepository()

    fun login(employeeID: String, password: String): MutableLiveData<Boolean> {
        repo.save("eid",employeeID)
        return userRepository.login(employeeID, password)
    }
}








