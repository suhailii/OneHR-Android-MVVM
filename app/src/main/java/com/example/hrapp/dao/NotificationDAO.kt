package com.example.hrapp.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import data.Notification

/***
 * Data Access Object Interface for Notification records.
 * This DAO interface is used to retrieve, insert and delete Notification objects
 ***/

@Dao
interface NotificationDAO {

    @Query("SELECT * FROM notification_table")
    fun getAllNotification() : LiveData<List<Notification>?>

    @Insert
    fun insert(notification : Notification)

    @Query("DELETE FROM notification_table")
    fun deleteAll()
}