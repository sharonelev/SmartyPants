package com.appsbysha.saywhat.database

import androidx.room.TypeConverter
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.model.Line
import com.appsbysha.saywhat.model.Saying
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * Created by sharone on 09/01/2025.
 */
class ChildHashMapConverter {
    @TypeConverter
    fun fromHashMap(map: HashMap<String, Child>): String {
        return Gson().toJson(map)
    }
    @TypeConverter
    fun toHashMap(json: String): HashMap<String, Child> {
        val type = object : TypeToken<HashMap<String, Child>>() {}.type
        return Gson().fromJson(json, type)
    }
}

class SayingMapConverter {
    @TypeConverter
    fun fromHashMap(map: HashMap<String, Saying>): String {
        return Gson().toJson(map)
    }
    @TypeConverter
    fun toHashMap(json: String): HashMap<String, Saying> {
        val type = object : TypeToken<HashMap<String, Saying>>() {}.type
        return Gson().fromJson(json, type)
    }
}



class LineListConverter {
    @TypeConverter
    fun fromLineList(lineList: List<Line>): String {
        return Gson().toJson(lineList)
    }

    @TypeConverter
    fun toLineList(json: String): List<Line> {
        val type = object : TypeToken<List<Line>>() {}.type
        return Gson().fromJson(json, type)
    }
}