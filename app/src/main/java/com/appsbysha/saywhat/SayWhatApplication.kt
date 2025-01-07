package com.appsbysha.saywhat

import android.app.Application
import com.google.firebase.FirebaseApp

/**
 * Created by sharone on 06/01/2025.
 */


class SayWhatApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}