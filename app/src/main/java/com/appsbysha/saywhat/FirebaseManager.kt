package com.appsbysha.saywhat

import android.util.Log
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.model.Saying
import com.appsbysha.saywhat.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

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

suspend fun listenToUserData(userId: String, onUserDataChange: (User?) -> Unit) {
    val database = Firebase.database

    withContext(Dispatchers.IO) {
        val userRef = database.getReference("users").child(userId)

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataMap = snapshot.value as? Map<*, *>
                val gson = Gson()
                val jsonString = gson.toJson(dataMap)
                val user = gson.fromJson(jsonString, User::class.java)
                onUserDataChange(user)

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase_TEST", "Error getting data", error.toException())
                onUserDataChange(null)
            }
        })
    }
}


suspend fun uploadSayingToFirebase(child: Child, saying: Saying) {
    val database = FirebaseDatabase.getInstance()
    val sayingRef = database.getReference("users").child("sha171").child("children").child(child.childId).child("sayings")
    val updates = hashMapOf<String, Any>(
        saying.id to saying
    )
    withContext(Dispatchers.IO) {
        sayingRef.updateChildren(updates).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Firebase_TEST", "Update successful")
            } else {
                Log.d("Firebase_TEST", "Update failed", task.exception)
            }
        }
    }
}