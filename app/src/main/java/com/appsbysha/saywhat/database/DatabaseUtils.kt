package com.appsbysha.saywhat.database

import androidx.room.TypeConverter
import com.appsbysha.saywhat.SayWhatApplication
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.model.User
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type


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


    suspend fun saveChildToLocalDatabase(appContext: SayWhatApplication, child: Child) {

        }

    }
