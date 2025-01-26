package com.appsbysha.saywhat

import android.app.Application
import com.appsbysha.saywhat.di.appModule
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by sharone on 06/01/2025.
 */


class SayWhatApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@SayWhatApplication)
            modules(appModule)
        }

        FirebaseApp.initializeApp(this)
    }
}