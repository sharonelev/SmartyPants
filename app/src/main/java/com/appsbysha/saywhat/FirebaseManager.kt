package com.appsbysha.saywhat

import android.util.Log
import com.appsbysha.saywhat.model.User
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

suspend fun getUserData(userId: String): User? {
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
        Log.e("Firebase", "Error getting data", e)
        null
    }
}