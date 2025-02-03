package com.appsbysha.saywhat.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.appsbysha.saywhat.viewmodels.AuthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

/**
 * Created by sharone on 03/02/2025.
 */


class AuthActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel =
            ViewModelProvider(this)[AuthViewModel::class.java]
// Initialize Firebase Auth
        auth = Firebase.auth
        setContent { AuthView(authViewModel) }
        observeLogin()
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            authViewModel.loadLoggedInUser()
        }
    }

    fun observeLogin() {
        authViewModel.userLoggerIn.observe(this){
            if(it) {
                startMainActivity()
            }
        }
    }

    private fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}