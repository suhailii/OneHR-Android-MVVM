package com.example.hrapp

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationUtils private constructor(){

    companion object {
        private var fusedLocationProviderClient: FusedLocationProviderClient?= null
        var location : MutableLiveData<Location> = MutableLiveData()


        // using singleton pattern to get the locationProviderClient
        fun getInstance(appContext: Context): FusedLocationProviderClient {
            if (fusedLocationProviderClient == null)
                fusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(appContext)
            return fusedLocationProviderClient!!
        }

        @SuppressLint("MissingPermission")
        fun getLocation() {
            fusedLocationProviderClient!!.lastLocation
                .addOnSuccessListener { loc: Location? ->
                    location.value = loc
                }

            //return location
        }
    }

}