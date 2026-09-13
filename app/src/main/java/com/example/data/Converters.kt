package com.example.data

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {

    @TypeConverter
    fun fromAchievementsList(value: List<String>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toAchievementsList(value: String): List<String> {
        return try {
            Json.decodeFromString(value)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
