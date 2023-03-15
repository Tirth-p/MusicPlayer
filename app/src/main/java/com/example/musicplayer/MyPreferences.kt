package com.example.musicplayer

import android.content.Context

/**
 * Created by Tirth Patel.
 */
object MyPreferences {
    private const val PREFS_NAME = "my_prefs"

    fun setStringPref(context: Context, key: String, value: String) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }
    fun setIntPref(context: Context, key: String, position: Int) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(key, position)
        editor.apply()
    }
    fun setImagePref(context: Context, key: String, imageID: Int) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(key, imageID)
        editor.apply()
    }

    fun getStringPref(context: Context, key: String): String? {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, null)
    }

    fun getIntegerPref(context: Context, key: String): Int {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(key, 0)
    }


    fun checkIfKeySet(context: Context,key: String):Boolean{
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.contains(key)
    }
    // Add other shared preference methods as needed
}
