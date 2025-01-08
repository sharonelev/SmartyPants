package com.appsbysha.saywhat

import android.util.Log
import com.appsbysha.saywhat.model.Saying
import com.appsbysha.saywhat.model.User
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await

/**
 * Created by sharone on 07/01/2025.
 */




suspend fun saveUserData(userId: String, user: User) {
    val database = Firebase.database
    val userRef = database.getReference("users").child(userId)
    val gson = Gson()
    val jsonString = gson.toJson(user)
    userRef.setValue(jsonString).await()
}

suspend fun getUserDataJson(userId: String): User? {
    val database = Firebase.database
    val userRef = database.getReference("users").child(userId)
    return try {
        val snapshot = userRef.get().await()
        val jsonString = snapshot.getValue(String::class.java)
        jsonString?.let {
            val gson = Gson()
            gson.fromJson(it, User::class.java)
        }
    } catch (e: Exception) {
        Log.e("Firebase_TEST", "Error getting data", e)
        null
    }
}
suspend fun getUserData(userId: String): User? {
    val database = Firebase.database
    val userRef = database.getReference("users").child(userId)
    return try {
        val snapshot = userRef.get().await()
        val dataMap = snapshot.value as Map<String, Any>

        // Convert the Map to a JSON string
        val gson = Gson()
        val jsonString = gson.toJson(dataMap)
            jsonString?.let {
            gson.fromJson(it, User::class.java)
        }
    } catch (e: Exception) {
        Log.e("Firebase_TEST", "Error getting data", e)
        null
    }
}
suspend fun updateSayingLine(sayingPath:String, newLine: String){
    val database = FirebaseDatabase.getInstance()
    val sayingRef = database.getReference(sayingPath)
    val updates = hashMapOf<String, Any>(
        "text" to newLine
    )
    sayingRef.updateChildren(updates).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Log.d("Firebase_TEST", "Update successful")
        } else {
            Log.d("Firebase_TEST", "Update failed", task.exception)
        }
    }
}