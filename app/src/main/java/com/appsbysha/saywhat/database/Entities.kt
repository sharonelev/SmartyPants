package com.appsbysha.saywhat.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.model.Line
import com.appsbysha.saywhat.model.LineType
import com.appsbysha.saywhat.model.Saying
import java.util.UUID

/**
 * Created by sharone on 07/01/2025.
 */



@Entity
data class UserEntity(
    @PrimaryKey val userId: String,
    @TypeConverters(ChildHashMapConverter::class)
    val children: HashMap<String, Child> = hashMapOf()
)

@Entity
data class ChildEntity(
    @PrimaryKey val childId: String,
    val name: String,
    val dob: Long,
    val profilePic: Byte? = null,
    @TypeConverters(SayingMapConverter::class)
    val sayings: HashMap<String, Saying> = hashMapOf()
)

@Entity
data class LineEntity(
    @PrimaryKey val lineId: String,
    val lineType: LineType,
    val text: String,
    val otherPerson: String? = null
)

@Entity
data class SayingEntity(
    @PrimaryKey val sayingId: String,
    val date: Long,
    @TypeConverters(LineListConverter::class)
    val lineList: List<Line> = emptyList()
)