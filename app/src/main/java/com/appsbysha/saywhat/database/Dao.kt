package com.appsbysha.saywhat.database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
/**
 * Created by sharone on 07/01/2025.
 */


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM UserEntity WHERE userId = :userId")
    suspend fun getUser(userId: String): UserEntity?
}

@Dao
interface ChildDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChild(child: ChildEntity)

    @Query("SELECT * FROM ChildEntity WHERE childId = :childId")
    suspend fun getChild(childId: String): ChildEntity?
}

@Dao
interface SayingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSaying(saying: SayingEntity)

    @Query("SELECT * FROM SayingEntity WHERE sayingId = :sayingId")
    suspend fun getSaying(sayingId: String): SayingEntity?
}

@Dao
interface LineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLine(line: LineEntity)

    @Query("SELECT * FROM LineEntity WHERE lineId = :lineId")
    suspend fun getLine(lineId: String): LineEntity?
}