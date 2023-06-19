package com.example.hrapp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.hrapp.repository.SharedPrefRepository
import com.google.firebase.database.*
import data.Claim
import data.Leave

class NotifyService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val repoShared = SharedPrefRepository(this)
        val eid = repoShared.getValueString("eid").toString()

        val database: DatabaseReference =
            FirebaseDatabase.getInstance("https://hrapp-764e0-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
        /**
         *  Listener for leaves objects in Firebase
         *  Will call function if changes is done in firebase real time database
         *  if statements to check if status == "Approved" && id == employeeID
         *  else if status == "Rejected" && id == employeeID
         *  Send broadcast if true, update leaves status to NOTIFIEDA/NOTIFIEDR to prevent multiple notifications
         *  NOTIFIEDA = ACCEPTED
         *  NOTIFIEDR = REJECTED
         **/
        database.child("leaves").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                database.get().addOnSuccessListener {
                    if (it.exists() && it.child("leaves").exists()) {
                        val leave =
                            it.child("leaves").value as java.util.ArrayList<java.util.HashMap<String, Any?>>
                        val leavelist = arrayListOf<Leave>()
                        for (i in leave) {
                            if (i["status"].toString() == "Approved" && i["eid"].toString() == eid) {
                                val leaveType = i["type"].toString()
                                val leaveID = i["id"].toString()
                                val intent = Intent("com.example.action.NEW_DATA_RECEIVED_FROM_SERVER")
                                intent.putExtra("KEY1", "Leave ID $leaveID : $leaveType-----Status : Approved !")
                                sendBroadcast(intent)
                                Log.d("leave broadcast","sent")
                                val l = Leave(
                                    i["id"].toString(),
                                    i["eid"].toString(),
                                    i["endDate"].toString(),
                                    i["remarks"].toString(),
                                    i["startDate"].toString(),
                                    i["status"].toString(),
                                    i["type"].toString(),
                                    i["name"].toString()
                                )
                                leavelist.add(l)
                                //update status to notified so will not keep notifying due to approved status
                                database.child("leaves").child(i["id"].toString()).child("status").setValue("NOTIFIEDA")
                            }else if(i["status"].toString() == "Rejected" && i["eid"].toString() == eid){
                                val leaveType = i["type"].toString()
                                val leaveID = i["id"].toString()
                                val intent = Intent("com.example.action.NEW_DATA_RECEIVED_FROM_SERVER")
                                intent.putExtra("KEY1", "Leave ID $leaveID : $leaveType-----Status : Approved !")
                                sendBroadcast(intent)
                                Log.d("leave broadcast","sent")
                                val l = Leave(
                                    i["id"].toString(),
                                    i["eid"].toString(),
                                    i["endDate"].toString(),
                                    i["remarks"].toString(),
                                    i["startDate"].toString(),
                                    i["status"].toString(),
                                    i["type"].toString(),
                                    i["name"].toString()
                                )
                                leavelist.add(l)
                                //update status to notified so will not keep notifying due to approved status
                                database.child("leaves").child(i["id"].toString()).child("status").setValue("NOTIFIEDR")
                            }
                        }
                    }
                }.addOnFailureListener()
                {
                    Log.d("Firebase", "Failed")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("TEST", "Failed to read value.", error.toException())
            }
        })

        /**
         *  Listener for claims objects in Firebase
         *  Will call function if changes is done in firebase real time database
         *  if statements to check if status == "Approved" && id == employeeID
         *  else if status == "Rejected" && id == employeeID
         *  Send broadcast if true, update claim status to NOTIFIEDA/NOTIFIEDR to prevent multiple notifications
         *  NOTIFIEDA = ACCEPTED
         *  NOTIFIEDR = REJECTED
         **/

        database.child("claims").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                database.get().addOnSuccessListener {
                    if (it.exists() && it.child("claims").exists()) {
                        val claim =
                            it.child("claims").value as java.util.ArrayList<java.util.HashMap<String, Any?>>
                        val claimlist = arrayListOf<Claim>()
                        for (i in claim) {
                            if (i["status"].toString() == "Approved" && i["eid"].toString() == eid) {
                                val claimType = i["type"].toString()
                                val claimID = i["id"].toString()
                                val intent = Intent("com.example.action.NEW_DATA_RECEIVED_FROM_SERVER")
                                intent.putExtra("KEY1", "Claim ID $claimID : $claimType Claim -----Status : Approved !")
                                sendBroadcast(intent)
                                Log.d("claims broadcast","sent")
                                val l = Claim(
                                    i["id"].toString(),
                                    i["eid"].toString(),
                                    i["type"].toString(),
                                    i["status"].toString(),
                                    i["amount"].toString(),
                                    i["image"].toString(),
                                    i["remarks"].toString(),
                                    i["name"].toString()
                                )
                                claimlist.add(l)
                                //update status to notified so will not keep notifying due to approved status
                                database.child("claims").child(i["id"].toString()).child("status").setValue("NOTIFIEDA")
                            }else if(i["status"].toString() == "Rejected" && i["eid"].toString() == eid){
                                val claimType = i["type"].toString()
                                val claimID = i["id"].toString()
                                val intent = Intent("com.example.action.NEW_DATA_RECEIVED_FROM_SERVER")
                                intent.putExtra("KEY1", "Claim ID $claimID : $claimType Claim-----Status : Rejected !")
                                sendBroadcast(intent)
                                Log.d("claims broadcast","sent")
                                val l = Claim(
                                    i["id"].toString(),
                                    i["eid"].toString(),
                                    i["type"].toString(),
                                    i["status"].toString(),
                                    i["amount"].toString(),
                                    i["image"].toString(),
                                    i["remarks"].toString(),
                                    i["name"].toString()
                                )
                                claimlist.add(l)
                                //update status to notified so will not keep notifying due to approved status
                                database.child("claims").child(i["id"].toString()).child("status").setValue("NOTIFIEDR")
                            }
                        }
                    }
                }.addOnFailureListener()
                {
                    Log.d("Firebase", "Failed")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("TEST", "Failed to read value.", error.toException())
            }
        })

        /**
         *  Listener for attendance object in Firebase
         *  Notify user of attendance status and details.
         *  Will call function if changes is done in firebase real time database
         **/

        database.child("users").child(eid).child("attendance").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                database.child("users").child(eid).child("attendance").get().addOnSuccessListener {
                    if (it.exists()) {
                        val inout = it.child("inout").value
                        val time = it.child("time").value
                        val loc = it.child("location").value
                        if(inout.toString().toBoolean() == false){
                            val intent = Intent("com.example.action.NEW_DATA_RECEIVED_FROM_SERVER")
                            intent.putExtra("KEY1", "Currently Checked-Out : $time !-----Location : $loc")
                            sendBroadcast(intent)
                        }
                        else if (inout.toString().toBoolean() == true) {
                            val intent = Intent("com.example.action.NEW_DATA_RECEIVED_FROM_SERVER")
                            intent.putExtra("KEY1", "Currently Checked-In : $time !-----Location : $loc")
                            sendBroadcast(intent)
                        }
                        //sendBroadcast(intent)
                        Log.d("attendance broadcast","sent")

                    }
                }.addOnFailureListener()
                {
                    Log.d("Firebase", "Failed")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("TEST", "Failed to read value.", error.toException())
            }
        })

        /**
         *  Listener for roomID attribute in Firebase
         *  Notify user of the roomID of the Jitsi Meet Room ID created by Manager
         *  Will call function if changes is done in firebase real time database
         **/

        database.child("users").child("roomID").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                database.child("users").child("roomID").get().addOnSuccessListener {
                    if (it.exists()) {
                        val roomID = it.value
                        if (roomID != "NOTIFIED"){
                            val intent = Intent("com.example.action.NEW_DATA_RECEIVED_FROM_SERVER")
                            intent.putExtra("KEY1", "Conference: Room ID: -----Room ID : $roomID")
                            sendBroadcast(intent)
                            database.child("users").child("roomID").setValue("NOTIFIED")
                            Log.d("roomID broadcast","sent")
                        }
                    }
                }.addOnFailureListener()
                {
                    Log.d("Firebase", "Failed")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("TEST", "Failed to read value.", error.toException())
            }
        })
        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

}

