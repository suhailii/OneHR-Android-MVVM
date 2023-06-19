package com.example.hrapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.hrapp.database.NotificationDatabase
import com.example.hrapp.databinding.ActivityHomeBinding
import com.example.hrapp.ui.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import data.Notification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NewBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras
        val stringSplit = bundle!!.getString("KEY1")!!.split("-----")
        val title = stringSplit[0]
        val body = stringSplit[1]
        attendanceNotif(title, body, context)

        CoroutineScope(Dispatchers.IO).launch {
            val notif = Notification(0, title, body)
            val db = NotificationDatabase.getDatabase(context)
            db.notificationDAO().insert(notif)
        }
    }

    private fun attendanceNotif(title : String, body : String, context : Context){
        val data = Data.Builder()
            .putString("KEYTITLE", title)
            .putString("KEYBODY", body)
            .build()

        val notifManager = OneTimeWorkRequest.Builder(NotificationManager::class.java)
            .setInputData(data)
            .build()

        WorkManager
            .getInstance(context.applicationContext)
            .enqueue(notifManager)

    }
}


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var appBarConfiguration : AppBarConfiguration
    val br: BroadcastReceiver = NewBroadcastReceiver()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navView
        val i = Intent(this.applicationContext, NotifyService::class.java)
        this.applicationContext.startService(i)


        val filter = IntentFilter("com.example.action.NEW_DATA_RECEIVED_FROM_SERVER").apply {
        }
        registerReceiver(br, filter)




        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_leave,
                R.id.navigation_claims,
                R.id.navigation_notifications,
                R.id.navigation_jitsi
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



    }





    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}