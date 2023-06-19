package com.example.hrapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import data.Leave

/***
 * Repo for the Leaves objects from Firebase
 * Contains the CRUD functions
 * FirebaseDatabase contains leaves object
 ***/
class LeavesRepository {
    private val database: DatabaseReference =
        FirebaseDatabase.getInstance("https://hrapp-764e0-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
    /**
     * Adding leave object to Firebase and using attribute noOfLeaves in db to maintain id of leaves.
     * @param employeeID is the user employeeID
     * @param startDate is the starting date for the leave
     * @param endDate is the ending date for the leave
     * @param remarks is the remarks for the leave
     * @param type is the type of leave
     **/
    fun addLeave(
        employeeID: String,
        startDate: String,
        endDate: String,
        remarks: String,
        type: String
    ) {
        database.get().addOnSuccessListener {
            //Checking if employer exists with employeeID
            if (it.exists()) {
                //using noOfLeaves attributes for leaveID
                var noOfLeaves = it.child("noOfLeaves").value.toString().toInt()
                val name = it.child("users").child(employeeID).child("name").value.toString()
                val map: HashMap<String, String> = hashMapOf(
                    "id" to noOfLeaves.toString(),
                    "endDate" to endDate,
                    "remarks" to remarks,
                    "startDate" to startDate,
                    "status" to "Pending",
                    "type" to type,
                    "eid" to employeeID,
                    "name" to name
                )
                database.child("leaves").child(noOfLeaves.toString()).setValue(map)
                noOfLeaves += 1
                database.child("noOfLeaves").setValue(noOfLeaves.toString())
            }
        }.addOnFailureListener()
        {
        }

    }
    /**
     * Retrieving all of the leaves of employee through a List of Leaves object from Firebase.
     * Contains the EventListener to update LiveData whenever updated (Status will be updated by manager)
     * @param employeeID is the user employeeID
     **/
    fun retrieveLeaves(employeeID: String): MutableLiveData<List<Leave>?> {
        val list = MutableLiveData<List<Leave>?>()

        database.get().addOnSuccessListener {
            //Checking if employer exists with employeeID
            if (it.exists() && it.child("leaves").exists()) {
                val leave = it.child("leaves").value as ArrayList<HashMap<String, Any?>>
                val leavelist = arrayListOf<Leave>()
                for (i in leave) {
                    if (i["eid"].toString() == employeeID && i["status"].toString() != "Deleted") {
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
                    }

                }
                list.postValue(leavelist)
            } else {
                //TODO: no leaves available
            }
        }.addOnFailureListener()
        {
        }
        database.child(employeeID).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                database.get().addOnSuccessListener {
                    //Checking if employer exists with employeeID
                    if (it.exists() && it.child("leaves").exists()) {
                        val leave = it.child("leaves").value as ArrayList<HashMap<String, Any?>>
                        val leavelist = arrayListOf<Leave>()
                        for (i in leave) {
                            if (i["eid"].toString() == employeeID && i["status"].toString() != "Deleted") {
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
                            }

                        }
                        list.postValue(leavelist)
                    } else {
                        //TODO: no leaves available
                    }
                }.addOnFailureListener()
                {
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        return list
    }

    /**
     * Retrieving the selected leave object given by the leaveID.
     * Contains the EventListener to update LiveData whenever updated.
     * @param leaveID is the leave id selected by user.
     **/

    fun retrieveSelectedLeave(leaveID: String): MutableLiveData<Leave?> {
        val mutableLeave = MutableLiveData<Leave?>()

        database.get().addOnSuccessListener {
            //Checking if employer exists with employeeID
            if (it.exists() && it.child("leaves").child(leaveID).exists()) {
                val leave = it.child("leaves").child(leaveID).value as HashMap<String, Any?>
                val obj = Leave(
                    leave["id"].toString(),
                    leave["eid"].toString(),
                    leave["endDate"].toString(),
                    leave["remarks"].toString(),
                    leave["startDate"].toString(),
                    leave["status"].toString(),
                    leave["type"].toString(),
                    leave["name"].toString()
                )
                mutableLeave.postValue(obj)
            } else {
                //TODO: no leaves available
            }
        }.addOnFailureListener()
        {
        }
        return mutableLeave
    }

    /**
     * Edit the leave object with the relevant parameters in Firebase
     * @param employeeID is the user employeeID
     * @param startDate is the starting date for the leave
     * @param endDate is the ending date for the leave
     * @param remarks is the remarks for the leave
     * @param type is the type of leave
     **/

    fun editLeave(
        leaveID: String,
        employeeID: String,
        startDate: String,
        endDate: String,
        remarks: String,
        type: String
    ) {
        val map: HashMap<String, String> = hashMapOf(
            "id" to leaveID,
            "endDate" to endDate,
            "remarks" to remarks,
            "startDate" to startDate,
            "status" to "Pending",
            "type" to type,
            "eid" to employeeID
        )
        database.child("leaves").child(leaveID).setValue(map)
    }

    /**
     * Delete the selected leave object in Firebase
     * Instead of deleting the data, changing the status to deleted instead.
     * @param leaveID is the leave id selected by the user
     **/

    fun deleteLeave(leaveID: String) {
        database.get().addOnSuccessListener {
            //Checking if employer exists with employeeID
            if (it.exists()) {
                database.child("leaves").child(leaveID).child("status").setValue("Deleted")
            }
        }.addOnFailureListener()
        {
        }
    }

    /**
     * Retrieve all leaves with "Pending" as status for manager to view from Firebase
     * @param employeeID is the manager employer id so he/she will not be able to approve own leave.
     **/

    fun retrieveLeavesManager(employeeID: String): MutableLiveData<List<Leave>?> {
        val list = MutableLiveData<List<Leave>?>()

        database.get().addOnSuccessListener {
            //Checking if employer exists with employeeID
            if (it.exists() && it.child("leaves").exists()) {
                val leave = it.child("leaves").value as java.util.ArrayList<java.util.HashMap<String, Any?>>
                val leavelist = arrayListOf<Leave>()
                for (i in leave) {
                    if(i["status"].toString() == "Pending" && i["eid"].toString() != employeeID){
                        val obj = Leave(
                            i["id"].toString(),
                            i["eid"].toString(),
                            i["endDate"].toString(),
                            i["remarks"].toString(),
                            i["startDate"].toString(),
                            i["status"].toString(),
                            i["type"].toString(),
                            i["name"].toString()
                        )
                        leavelist.add(obj)
                    }

                }
                list.postValue(leavelist)
            } else {
            }
        }.addOnFailureListener()
        {
        }
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                database.get().addOnSuccessListener {
                    if (it.exists() && it.child("leaves").exists()) {
                        val leave = it.child("leaves").value as java.util.ArrayList<java.util.HashMap<String, Any?>>
                        val leavelist = arrayListOf<Leave>()
                        for (i in leave) {
                            Log.d("eid", i["eid"].toString())
                            if(i["status"].toString() == "Pending" && i["eid"].toString() != employeeID){
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
                            }
                        }
                        list.postValue(leavelist)
                    } else {

                    }
                }.addOnFailureListener()
                {
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return list
    }

    /**
     * Approve selected leave by changing status to "Approved" in Firebase
     * @param leaveID is the leave id that needs to be approved
     **/
    fun approveLeave(leaveID: String): MutableLiveData<Boolean?> {
        val sucess = MutableLiveData<Boolean?>()

        database.get().addOnSuccessListener {
            //Checking if employer exists with employeeID
            if (it.exists() && it.child("leaves").child(leaveID).exists()) {
                database.child("leaves").child(leaveID).child("status").setValue("Approved")
                sucess.postValue(true)
            } else {
                //TODO: no such leaves
                sucess.postValue(false)

            }
        }.addOnFailureListener()
        {
        }
        return sucess
    }
    /**
     * Reject selected leave by changing status to "Rejected" in Firebase
     * @param leaveID is the leave id that needs to be rejected
     **/
    fun rejectLeave(leaveID: String): MutableLiveData<Boolean?> {
        val sucess = MutableLiveData<Boolean?>()

        database.get().addOnSuccessListener {
            //Checking if employer exists with employeeID
            if (it.exists() && it.child("leaves").child(leaveID).exists()) {
                database.child("leaves").child(leaveID).child("status").setValue("Rejected")
                sucess.postValue(true)
            } else {
                //TODO: no such leaves
                sucess.postValue(false)

            }
        }.addOnFailureListener()
        {
        }
        return sucess
    }

    /**
     * Retrieve the latest leave details of user to display in homefragment
     * Contains EventListener to update accordingly to Firebase
     * @param employeeID is the employee id to retrieve the right leave
     **/
    fun retrieveLatestLeave(employeeID: String): MutableLiveData<Leave?> {
        val list = MutableLiveData<Leave?>()

        database.get().addOnSuccessListener {
            //Checking if employer exists with employeeID
            if (it.exists() && it.child("leaves").exists()) {
                val leave = it.child("leaves").value as java.util.ArrayList<java.util.HashMap<String, Any?>>
                val leavelist = arrayListOf<Leave>()
                for (i in leave) {
                    if(i["eid"].toString() == employeeID){
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
                    }

                }
                //Only retrieve last claim object
                if(leavelist.size != 0){
                    list.postValue(leavelist[leavelist.size-1])
                }
            } else {
                //TODO: no leaves available
            }
        }.addOnFailureListener()
        {
        }
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                database.get().addOnSuccessListener {
                    if (it.exists() && it.child("leaves").exists()) {
                        val leave = it.child("leaves").value as java.util.ArrayList<java.util.HashMap<String, Any?>>
                        val leavelist = arrayListOf<Leave>()
                        for (i in leave) {
                            Log.d("eid", i["eid"].toString())
                            if(i["status"].toString() == "Pending" && i["eid"].toString() == employeeID){
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
                            }
                        }
                        if(leavelist.size != 0){
                            list.postValue(leavelist[leavelist.size-1])
                        }
                    } else {

                    }
                }.addOnFailureListener()
                {
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return list
    }

    /**
     * Retrieve the latest leave that needs to be approved by manager in homefragment
     * @param employeeID is the manager id so he/she will not be able to approve own leave.
     * Contains EventListener to update accordingly to Firebase
     **/

    fun retrieveLatestLeaveManager(employeeID: String): MutableLiveData<Leave?> {
        val list = MutableLiveData<Leave?>()

        database.get().addOnSuccessListener {
            //Checking if employer exists with employeeID
            if (it.exists() && it.child("leaves").exists()) {
                val leave = it.child("leaves").value as java.util.ArrayList<java.util.HashMap<String, Any?>>
                val leavelist = arrayListOf<Leave>()
                for (i in leave) {
                    if(i["status"].toString() == "Pending" && i["eid"].toString() != employeeID){
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
                    }

                }
                //Only retrieve last claim object
                if(leavelist.size != 0){
                    list.postValue(leavelist[leavelist.size-1])
                }
            } else {
            }
        }.addOnFailureListener()
        {
        }
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                database.get().addOnSuccessListener {
                    if (it.exists() && it.child("leaves").exists()) {
                        val leave = it.child("leaves").value as java.util.ArrayList<java.util.HashMap<String, Any?>>
                        val leavelist = arrayListOf<Leave>()
                        for (i in leave) {
                            if(i["status"].toString() == "Pending" && i["eid"].toString() != employeeID){
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
                            }
                        }
                        if(leavelist.size != 0){
                            list.postValue(leavelist[leavelist.size-1])
                        }
                    } else {
                    }
                }.addOnFailureListener()
                {
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
        return list
    }
}