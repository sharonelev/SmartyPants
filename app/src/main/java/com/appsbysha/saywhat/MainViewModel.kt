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

/**
 * Created by sharone on 29/11/2024.
 */


class MainViewModel : ViewModel() {

    fun writeToDB() {
        // Write a message to the database

        val database = Firebase.database
        val adi = Child(
            dob = Utils.getDob("2017_02_16"),
            name = "Adi"
        )

      /*  val line1 = Line(LineType.MAIN_CHILD, "Let's order presents on Takealot")
        val line2 =
            Line(LineType.OTHER_PERSON, "We can't just buy presents whenever you want", "Ima")
        val line3 = Line(LineType.MAIN_CHILD, "We're not buying it, we're takeloting it")
        val saying1 = Saying(
            date = System.currentTimeMillis(),
            lineList = listOf(line1, line2, line3),
            id = "saying1"
        )*/

                val line1 = Line(LineType.NOTE, "משחקים לזהות חיות")
                val line2 = Line(LineType.OTHER_PERSON, "Roar", "Aba")
                val line3 = Line(LineType.MAIN_CHILD, "Lion")
                val line4 = Line(LineType.MAIN_CHILD, "Roar")
                val line5 = Line(LineType.OTHER_PERSON, "Lion", "Aba")
                val line6 = Line(LineType.MAIN_CHILD, "No, pineapple!")

                val saying2 = Saying(
                    date = System.currentTimeMillis(),
                    lineList = listOf(line1, line2, line3, line4, line5, line6),
                    id = "saying2"
                )
        adi.sayings.add(saying2)

        //try to use :    val myRef = database.getReference("users").child(userId).child("sayings").push()
        //    myRef.setValue(saying).await(). with try catch
        val myRef = database.getReference("users").child("sha171").child("Adi").child("sayings")
val say2 = mapOf("saying2" to saying2)
        myRef.updateChildren(say2).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Firebase", "Saying uploaded successfully")
            } else {
                Log.e("Firebase", "Failed to upload saying", task.exception)
            }
        }
    }
    private suspend fun  <T> getUserData(userId: String): T {

        val database = Firebase.database
        Log.d("Firebase_TEST", "getUserData")

        val userRef = database.getReference("users").child(userId)
        return try {
            val snapshot = userRef.get().await()
            Log.d("Firebase_TEST", "Data snapshot: ${snapshot.value}")
            snapshot.value as T
        } catch (e: Exception) {
            Log.e("Firebase_TEST", "Error getting data", e)
            null as T
        }

    }

    fun fetchUserData(userId: String) {
        Log.d("Firebase_TEST","fetchUserData")

        viewModelScope.launch {
            Log.d("Firebase_TEST","fetchUserData")

            try {
              //  var user: List<Saying>? = null
                val userJob = async(Dispatchers.IO) {
                   val user: User = getUserData(userId) as User
                    Log.d("Firebase_TEST","sha171::: $user")

                }
                userJob.await()
              //  Log.d("Firebase_TEST","sha171::: $user")
            }catch (exception: Exception){
                Log.d("Firebase_TEST","sha171::: ERROR")

            }

            // Handle the user data
        }
    }

}