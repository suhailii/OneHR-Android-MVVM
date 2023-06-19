package com.example.hrapp.repository

import androidx.lifecycle.LiveData
import com.example.hrapp.dao.NotificationDAO
import data.Notification
/***
 * Repo for the Notification objects for RoomDB
 ***/
class NotificationRepository (private val notificationDAO : NotificationDAO){
    /**
     * Retrieve all notification using the DAO
     **/
    fun getAllNotification() : LiveData<List<Notification>?> {
        return notificationDAO.getAllNotification()
    }
    /**
     * Delete all notifications using the DAO
     **/
    fun deleteAll(){
        notificationDAO.deleteAll()
    }
}