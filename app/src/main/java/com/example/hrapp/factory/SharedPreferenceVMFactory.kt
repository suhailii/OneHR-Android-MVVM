package com.example.hrapp.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hrapp.LoginActivityViewModel
import com.example.hrapp.repository.SharedPrefRepository
import com.example.hrapp.ui.claims.ClaimsListViewModel
import com.example.hrapp.ui.home.HomeViewModel
import com.example.hrapp.ui.jitsi.JitsiViewModel
import com.example.hrapp.ui.leaves.LeavesListViewModel
import com.example.hrapp.ui.leaves.LeavesViewModel
import com.example.hrapp.ui.leaves.SelectedLeaveViewModel
import com.example.hrapp.ui.managerview.ManageLeaveListViewModel
import com.example.hrapp.ui.managerview.ManagerClaimListViewModel

/***
 * Factory for the ViewModelProviders to return the correct View Model that requires the shared pref.
 * Shared pref contains employee id.
 ***/

class SharedPreferenceVMFactory(private val repo: SharedPrefRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                return HomeViewModel(repo) as T
            }
            modelClass.isAssignableFrom(LoginActivityViewModel::class.java) -> {
                return LoginActivityViewModel(repo) as T
            }
            modelClass.isAssignableFrom(ClaimsListViewModel::class.java) -> {
                return ClaimsListViewModel(repo) as T
            }
            modelClass.isAssignableFrom(LeavesViewModel::class.java) -> {
                return LeavesViewModel(repo) as T
            }
            modelClass.isAssignableFrom(LeavesListViewModel::class.java) -> {
                return LeavesListViewModel(repo) as T
            }
            modelClass.isAssignableFrom(SelectedLeaveViewModel::class.java) -> {
                return SelectedLeaveViewModel(repo) as T
            }
            modelClass.isAssignableFrom(ManageLeaveListViewModel::class.java) -> {
                return ManageLeaveListViewModel(repo) as T
            }
            modelClass.isAssignableFrom(ManagerClaimListViewModel::class.java) -> {
                return ManagerClaimListViewModel(repo) as T
            }
            modelClass.isAssignableFrom(JitsiViewModel::class.java) -> {
                return JitsiViewModel(repo) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}