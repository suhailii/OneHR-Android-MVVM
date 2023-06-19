package com.example.hrapp.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hrapp.repository.*
import data.Attendance
import data.Claim
import data.Leave
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(private val repo : SharedPrefRepository) : ViewModel() {
    var curLocation : MutableLiveData<String> = MutableLiveData()
    private val attendanceRepository: AttendanceRepository = AttendanceRepository()
    private val claimRepository: ClaimRepository = ClaimRepository()
    private val leaveRepository: LeavesRepository = LeavesRepository()
    private val userRepository: UserRepository = UserRepository()

    val eid = repo.getValueString("eid").toString()





    /**
     * Attendance
     */

    fun updateAttendance(time: String, location: String, bool : Boolean){
        attendanceRepository.updateAttendance(eid,time,location, bool)
    }

    fun compareWorkplace(locationLatitude : String, locationLongtitude : String, bool : Boolean){

        val dateFormat = SimpleDateFormat("dd/M/yyyy")
        val timeFormat = SimpleDateFormat("hh:mm aa")
        val currentDate = dateFormat.format(Date())
        val currentTime = timeFormat.format(Date())

        Log.d("Current Date : " , currentDate.toString())
        Log.d("Current Time : ", currentTime.toString())
        Log.d("True = In, False = Out ", bool.toString())

        if (locationLatitude.substring(0,3) == "1.3" && locationLongtitude.substring(0,5) == "103.6"){
            curLocation.value = "Jurong"
            updateAttendance(currentTime.toString(), curLocation.value.toString(), bool)
        }
        else if (locationLatitude.substring(0,3) == "1.3" && locationLongtitude.substring(0,5) == "103.7"){
            curLocation.value = "CCK"
            updateAttendance(currentTime.toString(), curLocation.value.toString(), bool)
        }
        else if (locationLatitude.substring(0,3) == "1.3" && locationLongtitude.substring(0,5) == "103.8"){
            curLocation.value = "SIT@NYP"
            updateAttendance(currentTime.toString(), curLocation.value.toString(), bool)
        }
        else{
            curLocation.value = "Invalid Location"
        }
    }

    fun retrieveAttendance(): MutableLiveData<Attendance>{
        return attendanceRepository.retrieveAttendance(eid)
    }

    /**
     * Claims
     */

    fun retrieveLatestClaim(): MutableLiveData<Claim?>{
        return claimRepository.retrieveLatestClaim(eid)
    }

    fun retrieveLatestClaimManager(): MutableLiveData<Claim?>{
        return claimRepository.retrieveLatestClaimManager(eid)
    }

    /**
     * Leaves
     */

    fun retrieveLatestLeave(): MutableLiveData<Leave?>{
        return leaveRepository.retrieveLatestLeave(eid)
    }

    fun retrieveLatestLeaveManager(): MutableLiveData<Leave?>{
        return leaveRepository.retrieveLatestLeaveManager(eid)
    }

    /**
     * User
     */

    fun retrieveRole(): MutableLiveData<String?>{
        return userRepository.retrieveRole(eid)
    }

    fun retrieveRoomID(): MutableLiveData<String?>{
        return userRepository.retrieveRoomID()
    }


}
