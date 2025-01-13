package com.appsbysha.saywhat

import android.app.Application
import com.appsbysha.saywhat.database.AppDatabase
import com.appsbysha.saywhat.di.appModule
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by sharone on 06/01/2025.
 */


class SayWhatApplication: Application() {
    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@SayWhatApplication)
            modules(appModule)
        }
   /*     database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "my-database"
        ).build()*/
        FirebaseApp.initializeApp(this)
    }
}