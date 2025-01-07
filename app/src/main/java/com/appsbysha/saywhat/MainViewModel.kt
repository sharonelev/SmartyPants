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

    private final val TAG = "MainViewModel"
    private var allUsersdata = User()


    fun saveUser(userId: String, user: User) {
        viewModelScope.launch {
            saveUserData(userId, user)
            // Handle the result
        }
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

/*    fun writeToDB() {
        // Write a message to the database

        val database = Firebase.database
*//*        val yoav = Child(
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
        }*//*
      *//*  val line1 = Line(LineType.MAIN_CHILD, "Let's order presents on Takealot")
        val line2 =
            Line(LineType.OTHER_PERSON, "We can't just buy presents whenever you want", "Ima")
        val line3 = Line(LineType.MAIN_CHILD, "We're not buying it, we're takeloting it")
        val saying1 = Saying(
            date = System.currentTimeMillis(),
            lineList = listOf(line1, line2, line3),
            id = "saying1"
        )*//*

                val line1 = Line(LineType.OTHER_PERSON, "אני אמא שלך ומה אתה שלי?", "אמא")
                val line2 = Line(LineType.MAIN_CHILD, "האהבה שלך")

                val saying2 = Saying(
                    date = System.currentTimeMillis(),
                    lineList = listOf(line1, line2),
                )

        val say2Id = UUID.randomUUID().toString()
      //  yoav.sayings[say2Id] = saying2
        val say2 = mapOf(saying2.id to saying2)
        //try to use :    val myRef = database.getReference("users").child(userId).child("sayings").push()
        //    myRef.setValue(saying).await(). with try catch
        val myRef = database.getReference("users").child("sha171").child("children").child("b8ba3db9-0cab-4d14-8ece-4b016e776530").child("sayings")

        myRef.updateChildren(say2).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Firebase", "Saying uploaded successfully")
            } else {
                Log.e("Firebase", "Failed to upload saying", task.exception)
            }
        }
    }



    private suspend fun getUserData(userId: String): Child?{

        val database = Firebase.database
        Log.d("Firebase_TEST", "getUserData")

        val userRef = database.getReference("users").child(userId)
        return try {
            val snapshot = userRef.get().await()
            Log.d("Firebase_TEST", "Data snapshot: ${snapshot.value}")
            parseUserData(snapshot.value as Map<String, Any>)
    *//*        for(child in snapshot.children)
            {
                Log.d("Firebase_TEST", "Data snapshot CHILD: ${child.value}")
                for(saying in child.value as HashMap<*, *>)
                {
                    Log.d("Firebase_TEST", "Data snapshot SAYING: ${saying.value}")
                    for(line in saying.value as HashMap<*,*>)
                    {
                        Log.d("Firebase_TEST", "Data snapshot LINE: ${line.value}")

                    }

                }
            }*//*
            return null
        } catch (e: Exception) {
            Log.e("Firebase_TEST", "Error getting data", e)
            null
        }

    }



    fun fetchUserData(userId: String) {
        Log.d("Firebase_TEST","fetchUserData")

        viewModelScope.launch {
            Log.d("Firebase_TEST","fetchUserData")

            try {
              //  var user: List<Saying>? = null
                val userJob = async(Dispatchers.IO) {
                   val user: Child = getUserData(userId) as Child
                    Log.d("Firebase_TEST","sha171::: $Child")

                }
                userJob.await()
              //  Log.d("Firebase_TEST","sha171::: $user")
            }catch (exception: Exception){
                Log.d("Firebase_TEST","sha171::: ERROR")

            }

            // Handle the user data
        }
    }


    private fun parseUserData(data: Map<String, Any>): User {
        val parsedChildren:  MutableMap<String, List<Child>> = mutableMapOf()
        for(child in data)
        {
            if(child is Child)
            {
                allUsersdata.children[child.childId] = child
            }
            val sayingsMap = data["Adi"] as Map<String, Any>
            val sayings = sayingsMap["sayings"] as Map<String, Any>
            val parsedSayings = sayings.mapValues { entry ->
                val sayingData = entry.value as Map<String, Any>
                val date = sayingData["date"] as Long
                val lineListData = sayingData["lineList"] as List<Map<String, Any>>
                val lineList = lineListData.map { lineData ->
                    Line(
                        lineType = lineData["lineType"] as LineType,
                        text = lineData["text"] as String,
                        otherPerson = lineData["otherPerson"] as String?
                    )
                }
                Saying(date = date, lineList = lineList, id = entry.key)
            }
        }

        return User(children = parsedChildren as HashMap<String, Child>)
    }*/
}