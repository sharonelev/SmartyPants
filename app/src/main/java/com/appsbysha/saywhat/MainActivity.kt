package com.appsbysha.saywhat


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainViewModel: MainViewModel =
            ViewModelProvider(this)[MainViewModel::class.java]
        Log.d("Firebase_TEST","fetchUserData MAIN")

        mainViewModel.fetchUserData("sha171")
        setContent {



           MainView()
        }
    }


}