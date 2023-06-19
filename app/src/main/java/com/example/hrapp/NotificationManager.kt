package com.example.hrapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import android.app.NotificationManager


class NotificationManager(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    // Notification
    lateinit var notificationChannel: NotificationChannel
    lateinit var notificationManager: android.app.NotificationManager
    lateinit var builder: Notification.Builder
    private val channelId = "12345"
    private val description = "Test Notification"

    /*** Do work here ***/
    override suspend fun doWork(): Result {

        /*** Get Input Data back using "inputData" variable ***/
        val title = inputData.getString("KEYTITLE")
        val body = inputData.getString("KEYBODY")

        if (title != null) {
            if (body != null) {
                initNotif(title, body)
            }
        }

        // Indicate whether work is success
        return Result.success()
    }

    /*** Create and start notification ***/
    private fun initNotif(time : String, loc : String){

        notificationManager = this.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        //val intent = Intent(this.applicationContext, HomeActivity::class.java)
        val intent2 : Intent = Intent()
        val pendingIntent = PendingIntent.getActivity(this.applicationContext, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
            builder = Notification.Builder(this.applicationContext, channelId).setContentTitle(time).setContentText(loc).setSmallIcon(R.drawable.ic_notifications_black_24dp).setContentIntent(pendingIntent)
        }
        notificationManager.notify(12345, builder.build())
    }
}