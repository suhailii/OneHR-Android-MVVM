package com.example.hrapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.*
import data.Attendance

/***
 * Repo for the Attendance objects from Firebase
 * Contains the updating and retrieving functions
 ***/

class AttendanceRepository {
    private val database: DatabaseReference =
        FirebaseDatabase.getInstance("https://hrapp-764e0-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("users")

    /**
     * Updating attendance object on Firebase
     * @param employeeID is the user employeeID
     * @param time is the time of checked in/out
     * @param location is the location of office
     * @param inout is the boolean of whether user is checked in/out
     **/
    fun updateAttendance(employeeID: String, time: String, location: String, inout: Boolean) {
        database.child(employeeID).child("attendance").child("time").setValue(time)
        database.child(employeeID).child("attendance").child("location").setValue(location)
        database.child(employeeID).child("attendance").child("inout").setValue(inout)
    }

    /**
     * Retrieving object from Firebase.
     * If first time log in to app, set values to false, -, -
     * @param employeeID is the user employeeID
     **/
    fun retrieveAttendance(employeeID: String): MutableLiveData<Attendance> {
        val attendanceObj = MutableLiveData<Attendance>()

        database.get().addOnSuccessListener {
            //Checking if employer exists with employeeID & contains attendance values
            if (it.exists() && it.child(employeeID).child("attendance").exists()) {
                val inOut = it.child(employeeID).child("attendance").child("inout").value
                val location = it.child(employeeID).child("attendance").child("location").value
                val time = it.child(employeeID).child("attendance").child("time").value
                val obj = Attendance(
                    employeeID,
                    time.toString(),
                    location.toString(),
                    inOut.toString().toBoolean()
                )
                attendanceObj.value = obj

            } else {
                //first time log in no attendance
                database.child(employeeID).child("attendance").child("inout").setValue(false)
                database.child(employeeID).child("attendance").child("location").setValue("-")
                database.child(employeeID).child("attendance").child("time").setValue("-")
                val obj = Attendance(employeeID, "-", "-", false)
                attendanceObj.value = obj

            }
        }.addOnFailureListener()
        {
            Log.d("Firebase", "Failed to retrieve DataSnapshot")
        }
        //Attached EventListener to database object with attendance to post value to attendanceObj [MutableLiveData<Attendance>]
        database.child(employeeID).child("attendance")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    database.get().addOnSuccessListener {
                        if (it.child(employeeID).child("attendance").exists()) {
                            val attendance =
                                it.child(employeeID)
                                    .child("attendance").value as HashMap<String, Any?>
                            Log.d("obj", attendance.toString())
                            val obj = Attendance(
                                employeeID,
                                attendance["time"].toString(),
                                attendance["location"].toString(),
                                attendance["inout"].toString().toBoolean()
                            )
                            attendanceObj.postValue(obj)
                            Log.d("ATTENDANCE OBJ", obj.toString())
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
        return attendanceObj


    }

}