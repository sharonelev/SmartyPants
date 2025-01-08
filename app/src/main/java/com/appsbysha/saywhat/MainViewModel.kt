package com.appsbysha.saywhat

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.model.Line
import com.appsbysha.saywhat.model.LineType
import com.appsbysha.saywhat.model.Saying
import com.appsbysha.saywhat.model.User
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import kotlinx.serialization.json.Json
import java.util.HashMap
import java.util.UUID

/**
 * Created by sharone on 29/11/2024.
 */


class MainViewModel : ViewModel() {

    private val TAG = "MainViewModel"


    fun saveUser(userId: String, user: User) {
        viewModelScope.launch {
            saveUserData(userId, user)
            // Handle the result
        }
    }

    fun updateSaying(){
        viewModelScope.launch { updateSayingLine("users/sha171/children/daf7fd05-ac60-4f0d-950e-da0295414525/sayings/df36afa7-bd71-4975-965f-9e72453e534e/lineList/0", "updated text success") }
    }


    fun fetchUserData(userId: String) {
        viewModelScope.launch {
            val user = getUserData(userId)
            // Handle the user data
            Log.d("Firebase_TEST", "User data: $user")
            if(user != null) {
                for (child in user.children) {
                    for(saying in child.value.sayings)
                    {
                        for(line in saying.value.lineList)
                        {
                            Log.d("Firebase_TEST", "child ${child.value.name} ${line.text}")

                        }
                    }
                }
            }
        }
    }


    fun writeToDB() {
        // Write a message to the database

        val database = Firebase.database
  /*      val yoav = Child(
        dob = Utils.getDob("2014_08_15"),
        name = "Yoav"
        )
        val myRef2 = database.getReference("users").child("sha171").child("children")

        val mapp = mapOf(yoav.childId to yoav)
        myRef2.updateChildren(mapp).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Firebase", "Saying uploaded successfully")
            } else {
                Log.e("Firebase", "Failed to upload saying", task.exception)
            }
        }*/

        val line1 = Line(LineType.OTHER_PERSON, "אני אמא שלך ומה אתה שלי?", "אמא")
        val line2 = Line(LineType.MAIN_CHILD, "האהבה שלך")

        val saying2 = Saying(
            date = System.currentTimeMillis(),
            lineList = listOf(line1, line2),
        )

        //  yoav.sayings[say2Id] = saying2
        val say2 = mapOf(saying2.id to saying2)
        //try to use :    val myRef = database.getReference("users").child(userId).child("sayings").push()
        //    myRef.setValue(saying).await(). with try catch
        val myRef = database.getReference("users").child("sha171").child("children").child("230e2abe-e2cd-4c75-9d7c-f5509540f2e7").child("sayings")

        myRef.updateChildren(say2).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Firebase", "Saying uploaded successfully")
            } else {
                Log.e("Firebase", "Failed to upload saying", task.exception)
            }
        }
    }



}