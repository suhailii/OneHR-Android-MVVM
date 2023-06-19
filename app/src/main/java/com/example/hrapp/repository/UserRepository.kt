package com.example.hrapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference

/***
 * Repo for the User objects from Firebase
 * Contains the login and other user related functions
 ***/
class UserRepository {
    private val database: DatabaseReference =
        FirebaseDatabase.getInstance("https://hrapp-764e0-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("users")

    fun login(employeeID: String, password: String): MutableLiveData<Boolean> {
        val accountExist = MutableLiveData<Boolean>()


        database.get().addOnSuccessListener {
            //Checking if employer exists with employeeID
            if (it.exists() && it.child(employeeID).exists()) {
                //Compare given password with firebase password.
                accountExist.value = password == it.child(employeeID).child("password").value
            } else {
                //set LiveData to false
                accountExist.value = false
            }
        }.addOnFailureListener()
        {
            Log.d("Firebase", "Failed")
        }
        return accountExist
    }

    fun retrieveRole(employeeID: String): MutableLiveData<String?> {
        val role = MutableLiveData<String?>()
        database.get().addOnSuccessListener {
            Log.d("Firebase Snapshot", it.toString())
            //Checking if employer exists with employeeID
            if (it.exists() && it.child(employeeID).exists()) {
                if (it.child(employeeID).child("role").value == "manager") {
                    role.postValue("manager")
                } else {
                    role.postValue("employee")
                }
            }
        }.addOnFailureListener()
        {
            Log.d("Firebase", "Failed")
        }
        return role
    }

    fun notifyRoomID(roomID: String) {
        database.get().addOnSuccessListener {
            Log.d("Firebase Snapshot", it.toString())
            if (it.exists()) {
                database.child("roomID").setValue(roomID)
                database.child("roomIDDisplay").setValue(roomID)

            }
        }.addOnFailureListener()
        {
            Log.d("Firebase", "Failed")
        }

    }

    fun retrieveRoomID(): MutableLiveData<String?> {
        val roomID = MutableLiveData<String?>()
        database.get().addOnSuccessListener {
            Log.d("Firebase Snapshot", it.toString())
            if (it.exists() && it.child("roomIDDisplay").exists()) {

                roomID.postValue(it.child("roomIDDisplay").value.toString())
            }
        }.addOnFailureListener()
        {
            Log.d("Firebase", "Failed")
        }
        database.child("roomIDDisplay").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                database.get().addOnSuccessListener {
                    //Checking if employer exists with employeeID
                    if (it.exists() && it.child("roomIDDisplay").exists()) {
                        roomID.postValue(it.child("roomIDDisplay").value.toString())
                    }
                }.addOnFailureListener()
                {
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("TEST", "Failed to read value.", error.toException())
            }
        })
        return roomID
    }


}