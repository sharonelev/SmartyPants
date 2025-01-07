package com.appsbysha.saywhat

import com.google.firebase.database.DataSnapshot
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Created by sharone on 06/01/2025.
 */


object Utils {

    fun getDob(yyyy_MM_dd: String):Long{
        // Define the date format
        val dateFormat = SimpleDateFormat("yyyy_MM_dd", Locale.getDefault())

        val date = dateFormat.parse(yyyy_MM_dd)

// Get the time in milliseconds
      return date?.time?: 0
    }

    fun parseDbToModel(snapshot:DataSnapshot){

    }
}