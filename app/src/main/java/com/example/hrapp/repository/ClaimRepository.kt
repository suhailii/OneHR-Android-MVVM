package com.example.hrapp.repository

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import android.graphics.Bitmap
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.util.*
import data.Claim

/***
 * Repo for the Claim objects from Firebase
 * Contains the CRUD functions
 * FirebaseStorage contains the images of claims
 * FirebaseDatabase contains claims object with image name that will be used to retrieve.
 ***/
class ClaimRepository {
    //Images stored here
    private var storageReference: StorageReference? = FirebaseStorage.getInstance().reference
    //Claims objects stored here with imageName only
    private val database: DatabaseReference =
        FirebaseDatabase.getInstance("https://hrapp-764e0-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

    /**
     * Uploading image to FirebaseStorage, will also call addClaim function to add to FirebaseDatabase
     * @param filePath is the Uri of image
     * @param employeeID is employee id of user
     * @param amount is the claim amount value
     * @param remarks is the remarks of claims.
     * @param type is the claim type.
     **/

    fun uploadImage(filePath: Uri?, employeeID: String, amount: String, remarks: String, type: String) {
        if (filePath != null) {
            val rando = UUID.randomUUID().toString()
            val ref = storageReference?.child("uploads/claims$rando")
            val imageName = "claims$rando"
            ref?.putFile(filePath)
            //addClaim function adds to FirebaseDatabase the object
            addClaim(employeeID,amount,remarks,imageName,type)
        } else {
        }
    }

    /**
     * Adding claim to FirebaseDatabase
     * Uses an attribute to increment the ID
     * @param employeeID is employee id of user
     * @param amount is the claim amount value
     * @param remarks is the remarks of claims.
     * @param image is the imageName of the file to be retrieved next time.
     * @param type is the claim type.
     **/
    private fun addClaim(employeeID: String, amount: String, remarks: String, image: String, type: String) {
        database.get().addOnSuccessListener {
            if (it.exists()) {
                val name = it.child("users").child(employeeID).child("name").value.toString()
                //check noOfClaims for claimID
                var noOfClaims = it.child("noOfClaims").value.toString().toInt()
                val map: HashMap<String, String> = hashMapOf(
                    "id" to noOfClaims.toString(),
                    "amount" to amount,
                    "remarks" to remarks,
                    "image" to image,
                    "status" to "Pending",
                    "type" to type,
                    "eid" to employeeID,
                    "name" to name
                )
                database.child("claims").child(noOfClaims.toString()).setValue(map)
                noOfClaims += 1
                database.child("noOfClaims").setValue(noOfClaims.toString())
            }
        }.addOnFailureListener()
        {
        }

    }
    /**
     * Retrieving claim from FirebaseDatabase
     * Contains listener to have live updates of claim objects
     * Uses an attribute to increment the ID
     * @param employeeID is employee id of user
     **/
    fun retrieveClaims(employeeID: String): MutableLiveData<List<Claim>?> {
        val list = MutableLiveData<List<Claim>?>()

        database.get().addOnSuccessListener {
            //Checking if employer exists with employeeID
            if (it.exists() && it.child("claims").exists()) {
                val claim = it.child("claims").value as ArrayList<HashMap<String, Any?>>
                val claimlist = arrayListOf<Claim>()
                for (i in claim) {
                    if (i["eid"].toString() == employeeID) {
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
                    }

                }
                list.postValue(claimlist)
            } else {
            }
        }.addOnFailureListener()
        {
        }
        database.child("claims").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                database.get().addOnSuccessListener {
                    //Checking if employer exists with employeeID
                    if (it.exists() && it.child("claims").exists()) {
                        val claim = it.child("claims").value as ArrayList<HashMap<String, Any?>>
                        val claimlist = arrayListOf<Claim>()
                        for (i in claim) {
                            if (i["eid"].toString() == employeeID) {
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
                            }

                        }
                        list.postValue(claimlist)
                    } else {
                    }
                }.addOnFailureListener()
                {
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("TEST", "Failed to read value.", error.toException())
            }
        })
        return list
    }
    /**
     * Retrieve the selected claim details from FirebaseDatabase
     * @param claimID is claim id of selected claim
     **/
    fun retrieveSelectedClaim(claimID: String): MutableLiveData<Claim?> {
        val mutableCLaim = MutableLiveData<Claim?>()

        database.get().addOnSuccessListener {
            //Checking if employer exists with employeeID
            if (it.exists() && it.child("claims").child(claimID).exists()) {
                val claim = it.child("claims").child(claimID).value as HashMap<String, Any?>
                val obj = Claim(
                    claim["id"].toString(),
                    claim["eid"].toString(),
                    claim["type"].toString(),
                    claim["status"].toString(),
                    claim["amount"].toString(),
                    claim["image"].toString(),
                    claim["remarks"].toString(),
                    claim["name"].toString()
                )
                mutableCLaim.postValue(obj)
            } else {
            }
        }.addOnFailureListener()
        {
        }
        return mutableCLaim
    }
    /**
     * Retrieving image from FirebaseStorage
     * @param image is image name stored in FirebaseDatabase
     **/
    fun retrieveStorageImage(image: String): MutableLiveData<Bitmap?>{
        val img = MutableLiveData<Bitmap?>()
        val storageRef= FirebaseStorage.getInstance("gs://hrapp-764e0.appspot.com").reference.child("uploads/$image")
        val localfile= File.createTempFile("tempImage","jpg")
        storageRef.getFile(localfile).addOnSuccessListener{
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            img.postValue(bitmap)
        }.addOnFailureListener{
        }
        return img
    }
    /**
     * Retrieving all claims for the manager to approve/reject
     * Contains listen for real time updates
     * Manager will not be able to approve/reject own leave
     * @param employeeID is employee id of user
     **/
    fun retrieveClaimsManager(employeeID: String): MutableLiveData<List<Claim>?> {
        val list = MutableLiveData<List<Claim>?>()
        database.get().addOnSuccessListener {
            //Checking if employer exists with employeeID
            if (it.exists() && it.child("claims").exists()) {
                val claim = it.child("claims").value as ArrayList<HashMap<String, Any?>>
                val claimlist = arrayListOf<Claim>()
                for (i in claim) {
                    if(i["status"].toString() == "Pending" && i["eid"].toString() != employeeID){
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
                    }
                }
                list.postValue(claimlist)
            } else {
            }
        }.addOnFailureListener()
        {
            Log.d("Firebase", "Failed")
        }
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                database.get().addOnSuccessListener {
                    if (it.exists() && it.child("claims").exists()) {
                        val claim = it.child("claims").value as ArrayList<HashMap<String, Any?>>
                        val claimlist = arrayListOf<Claim>()
                        for (i in claim) {
                            if(i["status"].toString() == "Pending" && i["eid"].toString() != employeeID){
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
                            }
                        }
                        list.postValue(claimlist)
                    } else {
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
        return list
    }
    /**
     * Approve claim selected by Manager
     * @param claimID is claim id of the claim that is going to be approved
     **/
    fun approveClaim(claimID: String): MutableLiveData<Boolean?> {
        val sucess = MutableLiveData<Boolean?>()
        database.get().addOnSuccessListener {
            if (it.exists() && it.child("claims").child(claimID).exists()) {
                database.child("claims").child(claimID).child("status").setValue("Approved")
                sucess.postValue(true)
            } else {
                //TODO: no such claims
                sucess.postValue(false)
            }
        }.addOnFailureListener()
        {
        }
        return sucess
    }
    /**
     * Reject claim selected by Manager
     * @param claimID is claim id of the claim that is going to be rejected
     **/
    fun rejectClaim(claimID: String): MutableLiveData<Boolean?> {
        val sucess = MutableLiveData<Boolean?>()
        database.get().addOnSuccessListener {
            if (it.exists() && it.child("claims").child(claimID).exists()) {
                database.child("claims").child(claimID).child("status").setValue("Rejected")
                sucess.postValue(true)
            } else {
                sucess.postValue(false)
            }
        }.addOnFailureListener()
        {
        }
        return sucess
    }
    /**
     * Retrieve latest claim to display on home fragment
     * Listener for real time updates
     * @param employeeID is employee id of user
     **/
    fun retrieveLatestClaim(employeeID: String): MutableLiveData<Claim?> {
        val list = MutableLiveData<Claim?>()

        database.get().addOnSuccessListener {
            if (it.exists() && it.child("claims").exists()) {
                val claim = it.child("claims").value as ArrayList<HashMap<String, Any?>>
                val claimlist = arrayListOf<Claim>()
                for (i in claim) {
                    if(i["eid"].toString() == employeeID){
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
                    }

                }
                //Only retrieve last claim object
                if(claimlist.size != 0){
                    list.postValue(claimlist[claimlist.size-1])
                }
            } else {
            }
        }.addOnFailureListener()
        {
        }
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                database.get().addOnSuccessListener {
                    if (it.exists() && it.child("claims").exists()) {
                        val leave = it.child("claims").value as ArrayList<HashMap<String, Any?>>
                        val claimlist = arrayListOf<Claim>()
                        for (i in leave) {
                            if(i["status"].toString() == "Pending" && i["eid"].toString() == employeeID){
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
                            }
                        }
                        if(claimlist.size != 0){
                            list.postValue(claimlist[claimlist.size-1])
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
     * Retrieve latest claim needed to be approved to display on home fragment
     * Listener for real time updates
     * @param employeeID is employee id of user
     **/
    fun retrieveLatestClaimManager(employeeID: String): MutableLiveData<Claim?> {
        val list = MutableLiveData<Claim?>()
        database.get().addOnSuccessListener {
            if (it.exists() && it.child("claims").exists()) {
                val claim = it.child("claims").value as ArrayList<HashMap<String, Any?>>
                val claimlist = arrayListOf<Claim>()
                for (i in claim) {
                    if(i["status"].toString() == "Pending" && i["eid"].toString() != employeeID){
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
                    }
                }
                //Only retrieve last claim object
                if(claimlist.size != 0){
                    list.postValue(claimlist[claimlist.size-1])
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
                    if (it.exists() && it.child("claims").exists()) {
                        val claim = it.child("claims").value as ArrayList<HashMap<String, Any?>>
                        val claimlist = arrayListOf<Claim>()
                        for (i in claim) {
                            if(i["status"].toString() == "Pending" && i["eid"].toString() != employeeID){
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
                            }
                        }
                        if(claimlist.size != 0){
                            list.postValue(claimlist[claimlist.size-1])
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