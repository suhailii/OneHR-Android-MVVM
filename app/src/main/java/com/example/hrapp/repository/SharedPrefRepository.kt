package com.example.hrapp.repository

import android.content.Context
import android.content.SharedPreferences
/***
 * Repo for the SharedPreference
 * Contains the saving and retrieving of shared preference item which is the employee id.
 ***/
class SharedPrefRepository(val context: Context) {
    private val PREFERENCE_NAME = "EmployeeID"
    private val pref: SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    /**
     * Save the employee id to SharedPreference
     * @param KEY_NAME is the name of key to retrieve next time
     * @param text is the value to store which is the eid.
     **/
    fun save(KEY_NAME: String, text: String) {

        val editor: SharedPreferences.Editor = pref.edit()

        editor.putString(KEY_NAME, text)

        editor.commit()
    }
    /**
     * Retrieve the employee id to SharedPreference
     * @param KEY_NAME is the name of key to retrieve
     **/
    fun getValueString(KEY_NAME: String): String? {

        return pref.getString(KEY_NAME, null)
    }
}