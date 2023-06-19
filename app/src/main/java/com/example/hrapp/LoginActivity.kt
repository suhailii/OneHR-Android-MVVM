package com.example.hrapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.hrapp.application.OneHR
import com.example.hrapp.databinding.ActivityLoginBinding
import com.example.hrapp.factory.SharedPreferenceVMFactory


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var homeLauncher: Intent

    private val MY_PERMISSIONS_REQUEST_LOCATION = 68
    private val REQUEST_CHECK_SETTINGS = 129

    private val loginActivityModel : LoginActivityViewModel by viewModels{
        SharedPreferenceVMFactory((this.application as OneHR).prefRepository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeLauncher = Intent(this, HomeActivity::class.java)

        //initialize the variables to use
        val btnLogin = binding.btnLogin
        val tvEID = binding.etEmployeeID
        val tvPassword = binding.etPassword

        btnLogin.setOnClickListener{
            val eid = tvEID.text.toString()
            val password = tvPassword.text.toString()
            login(eid,password)
        }
        val fineLocPerm = Manifest.permission.ACCESS_FINE_LOCATION
        checkPermission(fineLocPerm)
    }

    private fun checkPermission(locPerm : String){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@LoginActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION)
        } else {
            Log.e("MainActivity:","Location Permission Already Granted")
            if (getLocationMode() != 3) {
                showAlertDialog(this@LoginActivity)
            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Log.e("MainActivity:","Location Permission Granted")
                    if (getLocationMode() != 3) {
                        showAlertDialog(this@LoginActivity)
                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permission not granted. Some functions may not work properly", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

    private fun getLocationMode(): Int {
        return Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE)
    }

    fun login(employeeID: String, password: String) {
        loginActivityModel.login(employeeID, password).observe(this, Observer {
            Log.d("it",it.toString())
            if (it){
                //Goes to HomeActivity upon success
                startActivity(homeLauncher)
                //remove loginActivity from backstack
                finish()
            }else{
                Toast.makeText(this, "Invalid employee ID/ password. \nPlease try again!", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun showAlertDialog(context: Context?) {
        try {
            context?.let {
                val builder = AlertDialog.Builder(it)
                builder.setTitle(it.resources.getString(R.string.app_name))
                    .setMessage("Please select High accuracy Location Mode from Mode Settings")
                    .setPositiveButton(it.resources.getString(android.R.string.ok)) { dialog, which ->
                        dialog.dismiss()
                        startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), REQUEST_CHECK_SETTINGS)
                    }
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(false)
                    .show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}




//
//        //Testing update of database using button
//        buttonUpdate.setOnClickListener{
//            //url of FirebaseDatabase since using SEA instance
//            val database = FirebaseDatabase.getInstance("https://hrapp-764e0-default-rtdb.asia-southeast1.firebasedatabase.app/")
//            //reference to add the key and values
//            val myRef = database.getReference("testing")
//            //.child("keyName")
//            myRef.child("abc").setValue("testtest")
//            val intent = Intent(this,HomeActivity::class.java)
//            startActivity(intent)
//
//        }








