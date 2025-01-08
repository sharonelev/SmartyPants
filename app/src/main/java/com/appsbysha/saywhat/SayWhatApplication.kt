package com.appsbysha.saywhat

import android.app.Application
import androidx.room.Room
import com.appsbysha.saywhat.database.AppDatabase
import com.google.firebase.FirebaseApp

/**
 * Created by sharone on 06/01/2025.
 */


class SayWhatApplication: Application() {
    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
   /*     database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "my-database"
        ).build()*/
        FirebaseApp.initializeApp(this)
    }
}