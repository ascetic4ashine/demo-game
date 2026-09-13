package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "legacy_characters")
@Serializable
data class LegacyCharacter(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val inheritedWealth: Long,
    val timeOfDeath: Long = System.currentTimeMillis(),
    val achievements: List<String>
)
