package com.appsbysha.saywhat.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by sharone on 07/01/2025.
 */

@Database(entities = [UserEntity::class, ChildEntity::class, SayingEntity::class, LineEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun childDao(): ChildDao
    abstract fun sayingDao(): SayingDao
    abstract fun lineDao(): LineDao
}