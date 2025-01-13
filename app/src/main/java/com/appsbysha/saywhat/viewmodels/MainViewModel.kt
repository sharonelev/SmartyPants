package com.appsbysha.saywhat.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsbysha.saywhat.model.Line
import com.appsbysha.saywhat.model.LineType
import com.appsbysha.saywhat.model.Saying
import com.appsbysha.saywhat.model.User
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.appsbysha.saywhat.getUserData
import com.appsbysha.saywhat.saveUserData
import com.appsbysha.saywhat.updateSayingLine
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

/**
 * Created by sharone on 29/11/2024.
 */


open class MainViewModel(app: Application) : AndroidViewModel(app)  {

    private val TAG = "MainViewModel"


    /**
     *  on click on a saying opens the saying view with that saying
     */
    fun onSayingClicked(){

    }

    fun saveUser(userId: String, user: User) {
        viewModelScope.launch {
            saveUserData(userId, user)
            // Handle the result
        }
    }

    fun updateSaying(){
        viewModelScope.launch { updateSayingLine("users/sha171/children/daf7fd05-ac60-4f0d-950e-da0295414525/sayings/df36afa7-bd71-4975-965f-9e72453e534e/lineList/0", "updated text success") }
    }




    fun saveToLocalDb(){

    }


    fun writeToFB() {
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