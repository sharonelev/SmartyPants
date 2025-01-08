package com.appsbysha.saywhat.database

import android.app.Application
import android.content.Context
import com.appsbysha.saywhat.SayWhatApplication
import com.appsbysha.saywhat.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by sharone on 07/01/2025.
 */


object DatabaseUtils {

    suspend fun saveUserToLocalDatabase(appContext: SayWhatApplication, user: User) {
        withContext(Dispatchers.IO) {
            val userEntity = UserEntity(userId = "userId", children = user.children)
            val database = appContext.database
            database.userDao().insertUser(userEntity)
        }
    }
}