package com.appsbysha.saywhat.viewmodels

import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Created by sharone on 03/02/2025.
 */


class AuthViewModel: ViewModel() {
    private val TAG = "AuthViewModel"
    private var _email = MutableStateFlow("")
    val email = _email.asStateFlow()
    private var _password = MutableStateFlow("")
    val password = _password.asStateFlow()
    val userLoggerIn: MutableLiveData<Boolean> = MutableLiveData(false)

    fun createNewUser(){
        val auth = Firebase.auth
        auth.createUserWithEmailAndPassword(email.value, password.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    userLoggerIn.value = true
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
 /*                   Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()*/
                    //updateUI(null) //todo
                }
            }
    }


    fun loadLoggedInUser(){
        userLoggerIn.value = true
    }

    fun signInUser(){
        val auth = Firebase.auth
        auth.signInWithEmailAndPassword(email.value, password.value).addOnCompleteListener {task->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    //updateUI(user)
                    userLoggerIn.value = true
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
              /*      Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()*/
                  //  updateUI(null)
                }
            }
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }
}