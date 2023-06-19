package com.example.hrapp.ui.notifications

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hrapp.repository.NotificationRepository
import data.Notification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NotificationsViewModel (private val repo : NotificationRepository) : ViewModel() {

    lateinit var notifList : List<Notification>

    /*** Get list of notifications from room DB ***/
    fun getList() : LiveData<List<Notification>?>{
        Log.d("Notif VM : ", "Notif List retrieved")
        return repo.getAllNotification()
    }

    /*** Delete all notifications in the room DB ***/
    fun deleteAll(){
        CoroutineScope(Dispatchers.IO).launch {
            repo.deleteAll()
            Log.d("Notif VM : ", "Deleted All")
        }
    }
}

class NotificationsViewModelFactory(private val repo : NotificationRepository)
    : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>) : T {
            if (modelClass.isAssignableFrom(NotificationsViewModel::class.java)){
                return NotificationsViewModel(repo) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }