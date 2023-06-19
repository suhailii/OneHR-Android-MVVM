package com.example.hrapp.application

import android.app.Application
import com.example.hrapp.database.NotificationDatabase
import com.example.hrapp.repository.NotificationRepository
import com.example.hrapp.repository.SharedPrefRepository

/***
 * Global app context of HRApp
 ***/

class OneHR : Application() {
    //lazy init the db, notification repo and also shared preference repo.
    val db by lazy { NotificationDatabase.getDatabase(this)}
    val repo by lazy { NotificationRepository(db.notificationDAO())}
    val prefRepository by lazy { SharedPrefRepository(this) }

    
}